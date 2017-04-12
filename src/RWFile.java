import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by D.I. on 07.12.2016.
 */
public class RWFile {

    public static void SavetoFile(String msg, String rmq_name) {

        String current = null;
        try {
            current = new File( "." ).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(current + File.separator + rmq_name +".txt", true)) {

            writer.write(msg);
            writer.append(System.lineSeparator()); //append new line

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
