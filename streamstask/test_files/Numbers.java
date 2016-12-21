package com.xosmig.numbers;

import org.jetbrains.annotations.NotNull;
import com.xomig.maybe.Maybe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Numbers {
    @NotNull
    static private Maybe<Integer> readNumber(@NotNull Scanner fin) {
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
    public static void task(@NotNull Path input, @NotNull Path output) throws IOException {
        try (
                BufferedReader reader = Files.newBufferedReader(input);
                Scanner fin = new Scanner(reader);
                BufferedWriter writer = Files.newBufferedWriter(output)
        ) {
            while (fin.hasNext()) {
                Maybe<Integer> mb = readNumber(fin).map((Integer x) -> x * x);
                writer.write((mb.isPresent() ? mb.get().toString() : "null") + " ");
            }
        }
    }
}
