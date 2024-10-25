package wood.mike.functional.resourcehandling;

import java.io.FileWriter;
import java.io.IOException;

/**
 * FileWriterEAM encapsulates a heavy resource (Files) that need timely cleanup
 * Constructor and close methods are private
 */
public class FileWriterEAM  {
    private final FileWriter writer;

    private FileWriterEAM(final String fileName) throws IOException {
        writer = new FileWriter(fileName);
    }

    private void close() throws IOException {
        System.out.println("close called automatically...");
        writer.close();
    }

    public void writeStuff(final String message) throws IOException {
        writer.write(message);
    }
    //...

    /**
     * Factory method offers access to class, doesn't just return instance like typical factory method however.
     * Yields access and waits while user uses it then tidies up after.
     */
    public static void use(final String fileName,
                           final UseInstance<FileWriterEAM, IOException> block) throws IOException {

        final FileWriterEAM writerEAM = new FileWriterEAM(fileName);
        try {
            block.accept(writerEAM);
        } finally {
            writerEAM.close();
        }
    }

    public static void main(final String[] args) throws IOException {

        System.out.println("//" + "START:EAM_USE_OUTPUT");
        FileWriterEAM.use("eam.txt", writerEAM -> writerEAM.writeStuff("sweet"));
        System.out.println("//" + "END:EAM_USE_OUTPUT");

        FileWriterEAM.use("eam2.txt", writerEAM -> {
            writerEAM.writeStuff("how");
            writerEAM.writeStuff("sweet");
        });

    }

}
