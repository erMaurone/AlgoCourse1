package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 28/10/2014
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class FileOut {
    public static String directory = "src/main/resources/";

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private FileOut() {
    }

    public static void out(String content) {
        Throwable t = new Throwable();
        StackTraceElement callerClass = t.getStackTrace()[1];
         // getting caller name with reflection is deprecated - back in java 9
        //Class callerClass = sun.reflect.Reflection.getCallerClass(2);
        String[] classNameComponents = callerClass.toString().split("\\.");
        String fileName = classNameComponents[1] + ".txt";
        out(fileName, content, true);
    }

    public static void out(String filename, String content, boolean withTrailingLine) {
        if (content == null || content.length() == 0)
            return;
        Path logFile = Paths.get(directory + filename);
        try {
            if (!Files.isWritable(logFile)) {
                Set<PosixFilePermission> perms =
                        PosixFilePermissions.fromString("rwxr-x---");
                Files.createFile(logFile,
                        PosixFilePermissions.asFileAttribute(perms));
            }
            BufferedWriter writer =
                    Files.newBufferedWriter(logFile, UTF8,
                            StandardOpenOption.APPEND);
            writer.write(content);
            if (withTrailingLine) writer.newLine();

            writer.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
