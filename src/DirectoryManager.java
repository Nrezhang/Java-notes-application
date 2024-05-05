import java.io.*;
import java.util.Objects;

/**
 * Class responsible for all directory/file management tasks that
 * interact with the file system.
 * @deprecated
 */
public class DirectoryManager {

    private String path;

    public DirectoryManager() {}

    public File[] returnDirectories() {
        path = "app-data";
        File directory = new File(path);

        File[] directories = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        return (directories != null) ? directories : new File[0];
    }

    public File[] returnDirectoryContents(String folderName) {
        path = "app-data/" + folderName;
        File directory = new File(path);
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        return (files != null) ? files : new File[0];
    }

    public String makeDirectory(String name) {
        path = "app-data/" + name;
        File newDirectory = new File(path);
        if (!newDirectory.exists()) {
            boolean created = newDirectory.mkdir();
            if (created) {
                return "Folder created successfully.";
            } else {
                return "Failed to create folder.";
            }
        } else {
            return "Folder already exists.";
        }
    }

    public File makeNewFile(String folderName, String fileName) {
        path = "app-data/" + folderName + "/" + fileName;
        File newFile = new File(path);
        try {
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            e.printStackTrace();
        }
        return newFile;
    }


    public boolean deleteDirectory(String name) {
        path = "app-data/" + name;
        File directory = new File(path);
        if (directory.listFiles() != null) {
            for (File f : Objects.requireNonNull(directory.listFiles())) {
                f.delete();
            }
        }
        return directory.delete();
    }

    public boolean deleteFile(String folderName, String fileName) {
        String filePath = "app-data/" + folderName + "/" + fileName;
        File fileToDelete = new File(filePath);
        try{
            fileToDelete.delete();
            return true;
        }
        catch(SecurityException e){
            e.printStackTrace();
            return false;
        }
    }

    public String getFileContent(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public boolean setFileContent(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean renameDirectory(String oldName, String newName) {
        String oldPath = "app-data/" + oldName;
        String newPath = "app-data/" + newName;
        File oldDirectory = new File(oldPath);
        File newDirectory = new File(newPath);
        if (oldDirectory.exists() && !newDirectory.exists()) {
            return oldDirectory.renameTo(newDirectory);
        }
        return false;
    }
    
    public boolean renameFile(String folderName, String oldName, String newName) {
        String oldPath = "app-data/" + folderName + "/" + oldName;
        String newPath = "app-data/" + folderName + "/" + newName;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (oldFile.exists() && !newFile.exists()) {
            return oldFile.renameTo(newFile);
        }
        return false;
    }
}
