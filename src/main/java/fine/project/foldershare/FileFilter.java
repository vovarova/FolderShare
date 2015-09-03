package fine.project.foldershare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by volodymyrroman on 9/3/15.
 */
public final class FileFilter {
    private FileFilter (){}

    public static List<File> filter(List<File> files){
        ArrayList<File> result = new ArrayList<File>();
        for (File f : files){
            if(needToProcess(f)){
                result.add(f);
            }
        }
        return result;
    }

    public static boolean needToProcess(String fName){
        return fName!=null && !fName.startsWith(".");
    }

    public static boolean needToProcess(File fName){
        return fName!=null && !fName.getName().startsWith(".");
    }




}
