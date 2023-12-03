import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DownloadByUrl {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String address;

        while (!(address = sc.nextLine()).equalsIgnoreCase("exit")) {
            try {
                URL url = new URL(address);
                String[] paths = address.split("/");
                String filename = paths[paths.length - 1];

                // Replace invalid characters in the filename
                filename = filename.replaceAll("[^a-zA-Z0-9.-]", "_");

                // Check if the "files" directory exists, create if not
                Path directoryPath = Paths.get("files");
                if (Files.notExists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                try (InputStream is = url.openStream();
                     OutputStream os = new FileOutputStream("files/" + filename)) {
                    byte[] bytes = new byte[1024];
                    int len, downloaded = 0;

                    while ((len = is.read(bytes)) != -1) {
                        os.write(bytes, 0, len);
                        downloaded += len;
                        System.out.printf("\rDownloaded... %.2fkb", downloaded / 1000.0f);
                    }

                    System.out.println("\nDownload complete");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}

