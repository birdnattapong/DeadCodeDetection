package Files_Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class File_Reader {
    private List<String> paths = new ArrayList<>();
    private List<String> jarPath = new ArrayList<>();

    public List<String> readPath(String source) {
        try {
            File folder = new File(source);

            if (!folder.isDirectory() && source.matches(".+[.]java$")) {
                this.paths.add(source);
            } else {
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) { // Checking if the current file is directory or not.
                        String path = file.getAbsolutePath();
                        readPath(path);
                    } else {
                        if (file.getAbsolutePath().matches(".+[.]java$")) {
                            paths.add(file.getAbsolutePath());
                        }
                    }
                }
            }
            return this.getPaths();

        } catch (Exception e) {
            System.out.println("Error while parsing file, please check input source path and try again!");
            System.exit(0);
        }

        return this.getPaths();
    }

    public List<String> readJarPath(String jarLib){
        try{
            File folder = new File(jarLib);

            if(!folder.isDirectory() && jarLib.matches(".+[.]jar$")){
                this.jarPath.add(jarLib);
            }else {
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) { // Checking if the current file is directory or not.
                        String path = file.getAbsolutePath();
                        readJarPath(path);
                    } else {
                        if (file.getAbsolutePath().matches(".+[.]jar$")) {
                            jarPath.add(file.getAbsolutePath());
                        }
                    }
                }
            }
            return this.getJarPath();
        }catch (Exception e){
            System.out.println("Error while parsing file, please check input jar path and try again!");
            System.exit(0);
        }
        return this.getJarPath();
    }

    public List<String> getJarPath() {
        return jarPath;
    }

    public List<String> getPaths() {
        return paths;
    }
}