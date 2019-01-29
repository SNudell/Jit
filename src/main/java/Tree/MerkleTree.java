package Tree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import Constants.SerializeConstant;
import Utils.NodeReference;
import Utils.SerializeUtil;

//classic rekursiv tree implementation with added merkle functionality
public class MerkleTree implements Serializable {

    private TreeElement root;
    private NodeReference rootReference;


    public void storeData() {
        if(root == null) {
            return;
        }
        root.storeData();
        rootReference = new NodeReference(root.getHash() +".ser",true);
        SerializeUtil.write(root,rootReference.reference);
    }

    public String computeHash () {
        if(root == null) {
            return null;
        }
        return root.computeHash();
    }

    public void reconstruct(String directoryName) {
        try {
            String path = SerializeConstant.TEST_PATH + directoryName+"/";
            Files.createDirectories(Paths.get(path));
            root.reconstruct(path);

        } catch (IOException e) {
            System.out.println("problem creating directory to reconstruct to");
        }
    }

    public void serialize () throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(new File(SerializeConstant.SERIALIZE_PATH)));
        out.writeObject(root);
    }

    public void deserialize () throws ClassNotFoundException,IOException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(SerializeConstant.SERIALIZE_PATH)));
        root = (TreeElement) in.readObject();
    }

    public void insertNode(String path) {
        Node n = new Node(path);
        insert(n);
    }

    public void insertFileWithCompletePath(String path) {
        String parts[] = path.split("/");
        for(int j= 1; j<parts.length;j++) {
            parts[j] = parts[j-1]+"/"+parts[j];
        }
        String current;
        for(int i = 0; i <parts.length-1; i++) {
            current = parts[i];
            insertNode(current);
        }
        if(Files.isDirectory(Paths.get(path))) {
            insertNode(path);
            insertDirectoryContents(path);
        }else {
            insertLeaf(path);
        }
    }

    public void insertDirectoryContents (String path) {
        try {
            Stream<Path> pathsStream = Files.list(Paths.get(path));
            pathsStream.forEach(p -> {
                if (Files.isDirectory(p)) {
                    insertNode(p.toString());
                    insertDirectoryContents(p.toString());
                }else {
                    insertLeaf(p.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeEmptyDirs() {
        root.removeEmptyDirs();
    }

    public void insert(TreeElement tEle) {
        if(root == null) {
            root = tEle;
            return;
        }
        if(root.getPath().equals(tEle.getPath())) return;
        root.insert(tEle);
    }

    public void printTree() {
        if(root == null) {
            return;
        }
        System.out.println("\nPrinting Tree:");
        root.print();
    }

    public void insertLeaf(String path) {
        Leaf leaf = new Leaf(path);
        root.insert(leaf);
    }

    private void remove(TreeElement tEle) {
        if(root == null) {
            return;
        }
        root.remove(tEle.getPath());
    }

    public void remove(String path) {
        if(root == null) {
            return;
        }
        root.remove(path);
        removeEmptyDirs();
    }
}
