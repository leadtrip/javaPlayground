package wood.mike.functional;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class FileStuff {

    public static void main(String[] args) throws Exception{
        // all files in current directory
        Files.list(Paths.get("."))
                .forEach(System.out::println);

        // directories only
        Files.list(Paths.get("."))
                .filter(Files::isDirectory)
                .forEach(System.out::println);

        // files ending in .java only
        Files.newDirectoryStream(
                        Paths.get(".\\src\\main\\java\\wood\\mike\\functional"),
                        path -> path.toString().endsWith(".java"))
                .forEach(System.out::println);

        // hidden files only
        new File(".").listFiles(File::isHidden);

        watchFiles();
    }

    /**
     * Monitors current directory and prints names of any files changed within 1 minute
     * Just modify pom to get some output
     */
    private static void watchFiles() throws Exception{
        final Path path = Paths.get(".");
        final WatchService watchService =
                path.getFileSystem()
                        .newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Report any file changed within next 1 minute...");

        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
        if(watchKey != null) {
            watchKey.pollEvents()
                    .stream()
                    .forEach(event ->
                            System.out.println(event.context()));
        }
    }
}
