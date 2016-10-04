import com.xosmig.numbers.Numbers;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.txt");
            File outputFile = new File("output.txt");
            Numbers.task(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
