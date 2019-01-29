import Constants.SerializeConstant;
import Utils.SerializeUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Jit {

    public static void main(String args[]) {
        if(args.length == 0)
            return;
        switch(args[0]){
            case "init" :
                init();
                break;
            case "add" :
                add(args);
                break;
            case "print" :
                printStaging();
                break;
            case "commit":
                commit(args);
                break;
            case "callback":
                new StagingArea().reconstruct(args[1]+"ISCOMMIT.ser");
                break;
            case "list":
                printCommitList();
                break;
            case "delete":
                delete();
                break;
            case "remove":
                remove(args[1]);
                break;
            default :
                System.out.println("illegal arguments try add or init or print or callback");
        }
    }

    public static void remove(String path) {
        StagingArea.getStagingArea().remove(path).print().serialize();
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static void delete() {
       deleteDirectory(new File("./.jit"));
    }

    public static void printStaging() {
        StagingArea.getStagingArea().print();
    }

    public static void commit(String args[]) {
        StagingArea.getStagingArea().commit(args[1]).serialize();
    }

    public static void add(String args[]) {

        StagingArea sA = StagingArea.getStagingArea();
        for (int i = 1; i < args.length; i++) {
            if(Paths.get(args[i]).toFile().exists()) {
                sA.add(args[i]);
            }
            else {
                System.out.println("File doesnt exist: "+args[i]);
            }
        }
        sA.serialize();
    }

    public static void printCommitList() {
        try {
            Stream<Path> objectsContentStream = Files.list(Paths.get(SerializeConstant.OBJECTS_PATH));
            objectsContentStream.map(Path ::getFileName).forEach(s-> {
                if(s.toString().contains("ISCOMMIT")) {
                    Commit commit = SerializeUtil.read(s.toString());
                    String name = s.toString();
                    name = name.substring(0,name.length()-"ISCOMMIT.ser".length());
                    System.out.println(name+" message: "+commit.message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init () {
        try {
            Files.createDirectories(Paths.get("./.jit/staging"));
            Files.createDirectories(Paths.get("./.jit/objects"));
            //instead of deleting all the files and transfering them over i just put the restored files in here so i dont accidentally delete my project ;)
            Files.createDirectories(Paths.get("./.jit/testReconstructs"));
            StagingArea sA = new StagingArea();
            sA.serialize();
        }catch (Exception e) {

        }
    }
}
