package Tree;

import Constants.SerializeConstant;
import Utils.HashUtil;
import Utils.NodeReference;
import Utils.SerializeUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Node extends TreeElement{
    private List<TreeElement> children;
    Map<String, NodeReference> referenceMap;
    private String path;
    private String hashCode;

    public String getHash() {
        return this.hashCode;
    }

    public void storeData() {
        referenceMap = new HashMap<>();
        for(TreeElement tE : children) {
            tE.storeData();
            String reference = tE.getHash()+".ser";
            referenceMap.put(reference,new NodeReference(reference,true));
            SerializeUtil.write(tE, reference);
        }
    }

    public void reconstruct(String path) {
        try {
            Files.createDirectories(Paths.get(path+"/"+this.path));
            for(TreeElement child: children) {
                child.reconstruct(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String computeHash() {
        String childsHashCodes[] = new String[children.size()];
        for(int i = 0; i<children.size();i++) {
            childsHashCodes[i] = children.get(i).computeHash();
        }
        this.hashCode = HashUtil.hashAll(Arrays.stream(childsHashCodes));
        return hashCode;
    }

    public Node(String path) {
        this.path = path;
        children = new ArrayList<TreeElement>();
        this.hashCode = null;
        this.referenceMap = new HashMap<>();
    }

    public void serialize () throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(new File(SerializeConstant.SERIALIZE_PATH)));
        out.writeObject(children);
        out.writeObject(referenceMap);
    }

    public void deserialize () throws ClassNotFoundException,IOException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(SerializeConstant.SERIALIZE_PATH)));
        children = (List<TreeElement>) in.readObject();
    }

    public void print() {
        System.out.println(path);
        for(TreeElement current: children)  current.print();
    }

    public boolean insert(TreeElement tEle) {
        if(tEle.getPath().equals(path)){
            return true;
        }
        if(tEle.getPath().contains(path)) {
            for(TreeElement current: children) {
                if(current.insert(tEle)) {
                    return true;
                }
            }
            children.add(tEle);
            return true;
        }
        return false;
    }

    public boolean removeEmptyDirs() {
        for(int i = 0; i< children.size(); i++) {
            if(children.get(i).removeEmptyDirs()) {
                System.out.println("auto removed dir "+ children.get(i).getPath());
                children.remove(i);
            }
        }
        if(children.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean remove(String path) {
        if(this.path.equals(path))
            return true;
        if(!(path.contains(this.path)))
            return false;
        for(int i = 0; i<children.size(); i++) {
            if(children.get(i).remove(path)) {
                children.remove(children.get(i));
                System.out.println("removed child "+ path );
            }

        }
        return false;
    }

    public String getPath() {
        return path;
    }

}
