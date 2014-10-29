package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class IntFileGenerator {

    public static void main(String[] args) {
        if (argumentsAreValid(args))
            publishIntegers(Integer.valueOf(args[0]));
    }

    private static boolean argumentsAreValid(String[] arguments) {
        int argumentsCount = arguments.length;
        if (argumentsCount != 1)
            throw new IllegalArgumentException("expecting the number of integers to generate");
        return true;
    }

    private static void publishIntegers(int requestedIntegers) {
        String fileName = checkFileExists(requestedIntegers);
        int lineSize=20;
        int lastcount=0;
        StringBuilder line = new StringBuilder();
        int half = requestedIntegers /2;
        boolean lastInt = false;
        for(int i=1; i<half-lineSize; i++) {
                    generateLineOfValues(i, i+lineSize, line);
                    FileOut.out(fileName, line.toString(), !lastInt);
                    line = new StringBuilder();
                    i = i + lineSize;
                    lastcount = i;
        }
        int delta = requestedIntegers - lastcount*2;
        generateLineOfValues(half-delta+3, half, line);
        if (line.length() > 0)
            FileOut.out(fileName, line.toString(),lastInt);
    }

    private static String checkFileExists(int requestedIntegers) {
        String fileName = "generated_" + requestedIntegers + "_ints.txt";
        Path intFile = Paths.get("src/main/resources/" + fileName);
        if (Files.exists(intFile)) throw new IllegalArgumentException("int file already exists: " + fileName);
        return fileName;
    }

    private static void generateLineOfValues(int start, int end, StringBuilder line) {
        if (start < 0 || end < 0 || start >= end) throw new IllegalArgumentException("invalid parameters");
        int i = start;
        do {
            generatePosAndNegValue(line, i);
            i++;
        } while (i < end);
        line.append(i);
        line.append(" ");
        line.append(-i);
    }

    private static void generatePosAndNegValue(StringBuilder line, int i) {
        line.append(i);
        line.append(" ");
        line.append(-i);
        line.append(" ");
    }
}
