package Tree;

import Constants.SerializeConstant;
import Utils.HashUtil;
import Utils.NodeReference;

import java.io.*;
import java.nio.file.*;

public class Leaf extends TreeElement {
    private String path;
    private String hashCode;
    private NodeReference reference;

    public String computeHash() {
        File file = new File(path);
        try {
            this.hashCode = HashUtil.hash(new FileInputStream(file));
        }catch (IOException e) {
            System.out.println("error computing hash, reading File " +path);
            return null;
        }
        return hashCode;
    }

    public void storeData() {
        try {
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(Paths.get(path),Paths.get("./.jit/objects/"+hashCode+getFileType()),options);
        } catch (IOException e) {
            System.out.println("error copying file: "+path);
            e.printStackTrace();
        }
    }

    public void serialize () throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(new File(SerializeConstant.SERIALIZE_PATH)));
        out.writeObject(path);
    }

    public void deserialize () throws ClassNotFoundException,IOException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(SerializeConstant.SERIALIZE_PATH)));
    }

    public Leaf(String path) {
        this.path = path;
        this.hashCode = null;
    }

    private String getFileType(){
        String parts[] = path.split("\\.");
        return parts[parts.length-1];
    }

    private String getFileName() {
        String parts[] = path.split("/");
        return parts[parts.length-1];
    }

    public boolean removeEmptyDirs() {
        return false;
    }

    public void reconstruct(String path) {
        try {
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Path newPath = Paths.get(path+"/"+this.path);
            Files.copy(Paths.get(SerializeConstant.OBJECTS_PATH+hashCode+getFileType()),newPath,options);
        } catch (IOException e) {
            System.out.println("error creating new Commit IOError ");
            e.printStackTrace();
        }
    }

    public boolean insert(TreeElement tEle){return false;}

    public boolean remove(String path) {
        if(this.path.equals(path))
            return true;
        return false;
    }

    public String getHash() {
        return this.hashCode;
    }

    public String getPath() {
        return path;
    }

    public void print() {
        System.out.println(path+" is Leaf");
    }

}
