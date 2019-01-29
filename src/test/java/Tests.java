import Tree.MerkleTree;
public class Tests {

    public static void testTree() {
        MerkleTree tree = new MerkleTree();
        tree.insertNode(".jit");
        tree.insertNode(".jit/objects");
        tree.insertNode(".jit/staging");
        tree.insertNode(".jit/objects/firstObjects");
        tree.insertLeaf(".jit/objects/firstObjects/bla.txt");
        tree.insertLeaf(".jit/objects/f.txt");
        tree.insertNode(".jit/objects/firstObjects");
        tree.printTree();
        System.out.println();
        System.out.println("removing firstObjects");
        tree.remove(".jit/objects/firstObjects");
        tree.printTree();
    }

    public static void testInsert() {
        MerkleTree tree = new MerkleTree();
        tree.insertFileWithCompletePath(".jit/firstobjects/");
        tree.printTree();
    }

    public static void testFullPathInsert() {
        MerkleTree tree =  new MerkleTree();
        tree.insertFileWithCompletePath(".jit/objects/firstObjects/bla.txt");
        tree.insertFileWithCompletePath(".jit/objects/secondObjects/blub.txt");
        tree.printTree();
    }

    public static void main(String args[]) {
        testFullPathInsert();
    }

}
