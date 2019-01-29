package Utils;

import java.io.*;

public class NodeReference implements Serializable {
    public boolean isDir;
    public String reference;

    public NodeReference(String reference, boolean isDir){
        this.isDir = isDir;
        this.reference = reference;
    }

}
