import java.io.*;

import Constants.SerializeConstant;
import Tree.MerkleTree;
import Utils.HashUtil;
import Utils.NodeReference;
import Utils.SerializeUtil;

public class StagingArea {
    private transient MerkleTree tree;

    //call this to get the serialized staging area
    public static StagingArea getStagingArea() {
        StagingArea sA = new StagingArea();
        try {
            sA.deserialize();
        } catch (Exception e) {
            System.out.println("Initialized new Staging Area");
        }
        return sA;
    }

    //reconstructs a commit
    public StagingArea reconstruct(String commitPath) {
        Commit commit = SerializeUtil.read(commitPath);
        commit.reconstruct();
        return this;
    }


    public StagingArea commit(String message) {
        String treeHash = tree.computeHash();
        Commit commit = new Commit(message,new NodeReference(treeHash+".ser", true));
        String commitReference = HashUtil.hash((treeHash+message).getBytes())+"ISCOMMIT.ser";
        //this goes recursively into the tree and stores the important files for this particular commit
        tree.storeData();
        SerializeUtil.write(commit,commitReference);
        System.out.println("commit: "+commitReference.substring(0,commitReference.length()-"ISCOMMIT.ser".length()));
        return this;
    }

    public StagingArea print() {
        tree.printTree();
        return this;
    }

    public StagingArea() {
        tree = new MerkleTree();
    }

    public void serialize(){
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream(new File(SerializeConstant.SERIALIZE_PATH)));
            out.writeObject(tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StagingArea remove (String path) {
        tree.remove(path);
        return this;
    }

    public void deserialize () {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(SerializeConstant.SERIALIZE_PATH)));
            tree = (MerkleTree) in.readObject();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION while reading in merkle tree");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("classnotfoundexception when casting to merkle tree");
            e.printStackTrace();
        }
    }

    public void add(String path) {
        tree.insertFileWithCompletePath(path);
        tree.printTree();
    }

}
