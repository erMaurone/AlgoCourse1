package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileIn {
    private FileIn() {};

    public static int[] readInts(String filename) {
        int [] result = readFile(filename);
        return result;
    }

    private static int[] readFile(String filename) {
        Path path = Paths.get(filename);
        int[] result = new int[0];
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            while(reader.ready() ) {
                String line = reader.readLine();
                int[] columnsLine = processIntsInLine(line);
                result = concatenateIntArrays(columnsLine, result);
            }
        } catch(IOException ioe ) {
            System.out.println("could not process the file");
            ioe.printStackTrace();
        }
        return result;
    }

    private static int[] processIntsInLine(String line) {

        String [] columns = line.split("\\s+");
        int[] a = new int[columns.length];
        for (int i=0; i < columns.length;i++) {
            a[i] = Integer.valueOf(columns[i]);
        }
        return a;
    }

    public static int[] concatenateIntArrays(int[] array1, int[]array2) {
        int[] array1and2 = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, array1and2, 0, array1.length);
        System.arraycopy(array2, 0, array1and2, array1.length, array2.length);
        return array1and2;
    }
}
