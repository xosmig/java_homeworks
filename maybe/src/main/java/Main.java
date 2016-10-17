import com.xosmig.numbers.Numbers;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            Path inputFile = FileSystems.getDefault().getPath("input.txt");
            Path outputFile = FileSystems.getDefault().getPath("output.txt");
            Numbers.task(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
