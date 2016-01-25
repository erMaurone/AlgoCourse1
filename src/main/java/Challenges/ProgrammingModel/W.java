package Challenges.ProgrammingModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mauro on 19/01/2016.
 */
public class W {

    public static void get(String urlname, String filename) {
        try {
            URL source = new URL(urlname);
            File target = new File(filename);
            FileUtils.copyURLToFile(source, target);

        } catch(IOException ioe) { ioe.printStackTrace(); }

    }

    public static void main(String[] args) {

        String tmpdir =  "/tmp/";
        System.out.println("Enter url to be copied: ");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine();
        String filename = FilenameUtils.getName(url);
        String localFile = tmpdir + filename;

        W.get(url, localFile);

        File localCopy = new File(filename);
        if (localCopy.exists() && localCopy.isFile() && localCopy.length() > 0 )
            System.out.println(filename + " has been created.");

    }
}
