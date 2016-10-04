package com.xosmig.numbers;

import com.xomig.maybe.Maybe;

import java.io.*;
import java.util.Scanner;

public class Numbers {
    static private Maybe<Integer> readNumber(Scanner fin) {
        if (fin.hasNextInt()) {
            return Maybe.just(fin.nextInt());
        } else {
            fin.next();
            return Maybe.nothing();
        }
    }

    /**
     * Prints to the output file "null" instead of all not numbers in the input file
     * and squares instead of all numbers.
     */
    public static void task(File input, File output) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(input));
                Scanner fin = new Scanner(reader);
                BufferedWriter writer = new BufferedWriter(new FileWriter(output))
        ) {
            while (fin.hasNext()) {
                Maybe<Integer> mb = readNumber(fin).map((Integer x) -> x * x);
                writer.write((mb.isPresent() ? mb.get().toString() : "null") + " ");
            }
        }
    }
}
