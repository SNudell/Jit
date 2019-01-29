import Constants.SerializeConstant;
import Tree.MerkleTree;
import Tree.Node;
import Utils.NodeReference;
import Utils.SerializeUtil;

import java.io.*;

public class Commit implements Serializable {
    public String message;
    private NodeReference rootReference;

    public Commit (String message, NodeReference rootReference) {
        this.message = message;
        this.rootReference = rootReference;
    }

    public MerkleTree reconstruct () {
        MerkleTree tree = new MerkleTree();
        Node root = SerializeUtil.read(rootReference.reference);
        tree.insert(root);
        tree.printTree();
        tree.reconstruct(message);
        return tree;
    }
}
