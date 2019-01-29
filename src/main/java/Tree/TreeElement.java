package Tree;

import java.io.Serializable;

public abstract class TreeElement implements Serializable {
    public abstract void print();
    public abstract boolean insert(TreeElement tEle);
    public abstract String getPath();
    public abstract boolean remove(String path);
    public abstract String computeHash();
    public abstract void storeData();
    public abstract String getHash();
    public abstract void reconstruct(String path);
    public abstract boolean removeEmptyDirs();
}

