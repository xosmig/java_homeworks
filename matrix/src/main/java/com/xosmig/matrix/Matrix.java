package com.xosmig.matrix;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Created by Andrey Tonkikh on 18.09.16.
 * A class for square matrix N * N with fixed size.
 */
public class Matrix {
    private int[][] data;

    public Matrix(int size) {
        this(size, 0);
    }

    public Matrix(int size, int value) {
        data = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                set(y, x, value);
            }
        }
    }

    public int size() {
        return data.length;
    }

    public int get(int x, int y) {
        return data[y][x];
    }

    public void set(int x, int y, int value) {
        data[y][x] = value;
    }

    public void getSpiral() throws IOException {
        getSpiral(System.out);
    }

    public void getSpiral(PrintStream stream) throws IOException {
        if (size() % 2 == 0) {
            throw new IllegalArgumentException("Matrix: for spiral output length must be odd.");
        }

        final int centre = size() / 2;

        int x = size() / 2;
        int y = size() / 2;

        for (int layer = 0; layer <= size() / 2; layer++) {
            final int min = centre - layer;
            final int max = centre + layer;

            while (x > min) { // go up
                stream.print(get(x, y) + " ");
                x--;
            }

            while (y > min) { // go left
                stream.print(get(x, y) + " ");
                y--;
            }

            while (x < max) { // go down
                stream.print(get(x, y) + " ");
                x++;
            }

            while (y < max) { // go right
                stream.print(get(x, y) + " ");
                y++;
            }

            stream.print(get(x, y) + " ");
            y++; // one more step right to go to the next layer
        }
    }

    public void sortColumnsByFirstElement() {
        Arrays.sort(data, (int[] a, int[] b) -> Integer.compare(a[0], b[0]));
    }
}
