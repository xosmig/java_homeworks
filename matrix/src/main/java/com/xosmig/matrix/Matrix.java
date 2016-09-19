package com.xosmig.matrix;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * @author Andrey Tonkikh
 * A class for square matrix of of integers with fixed size.
 */
public class Matrix {
    private int[][] data;

    /**
     * Constructs the square matrix size * size and fills it with zeros.
     * @param size - the length of one side of the square matrix
     */
    public Matrix(int size) {
        this(size, 0);
    }

    /**
     * Constructs the square matrix size * size and fills it by the given value.
     * @param size - the length of one side of the square matrix
     * @param value - the value to initialize elements of the container with
     */
    public Matrix(int size, int value) {
        data = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                set(y, x, value);
            }
        }
    }

    /**
     * @return the length of one side of a square matrix
     */
    public int size() {
        return data.length;
    }

    /**
     * @param x - the number of the row in the matrix.
     * @param y - the number of the column in the matrix.
     * @return the element with the given coordinates in the matrix
     */
    public int get(int x, int y) {
        return data[y][x];
    }

    /**
     * @param x - the number of the row in the matrix.
     * @param y - the number of the column in the matrix.
     * @param value - new value for te element with coordinates (x, y).
     */
    public void set(int x, int y, int value) {
        data[y][x] = value;
    }

    /**
     * Prints items in a spiral manner to `stdout`.
     * @throws IOException if can't print to the `stdout`
     */
    public void getSpiral() throws IOException {
        getSpiral(System.out);
    }

    /**
     * Prints items in a spiral manner to the given PrintStream.
     * @param stream - stream to print in.
     * @throws IOException if can't print to the `stream`.
     */
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

    /**
     * Sorts columns by first elements with O(size log(size)) operations.
     */
    public void sortColumnsByFirstElement() {
        Arrays.sort(data, (int[] a, int[] b) -> Integer.compare(a[0], b[0]));
    }
}
