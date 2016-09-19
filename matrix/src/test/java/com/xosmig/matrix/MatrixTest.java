package com.xosmig.matrix;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


/**
 * Created by Andrey Tonkikh on 18.09.16.
 */
public class MatrixTest {

    @Test
    public void size() throws Exception {
        Matrix matr = new Matrix(8);
        assertEquals(matr.size(), 8);
    }

    @Test
    public void set() throws Exception {
        Matrix matr = new Matrix(23);
        assertEquals(matr.get(22, 22), 0);
        matr.set(22, 22, 2);
        assertEquals(matr.get(22, 22), 2);
        matr.set(22, 22, 123);
        assertEquals(matr.get(22, 22), 123);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set1() throws Exception {
        Matrix matr = new Matrix(5);
        matr.set(5, 5, 1);
    }

    @Test
    public void printSpiral() throws Exception {
        Matrix matr = new Matrix(5);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                matr.set(x, y, x * 5 + y + 1);
            }
        }

        // FIXME: Really horrible way to make a string-stream
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(buf);
        matr.getSpiral(stream);
        String res = new String(buf.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(res, "13 14 9 8 7 12 17 18 19 20 15 10 5 4 3 2 1 6 11 16 21 22 23 24 25 ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void printSpiral2() throws Exception {
        Matrix matr = new Matrix(12); // any even number
        matr.getSpiral();
    }

    private int magic(int i) {
        return (i * 1720 + 4319) ^ 2399;
    }

    private int magic(int x, int y) {
        return magic(x) * 8085 + magic(y);
    }

    @Test
    public void sortColumnsByFirstElement() throws Exception {
        Matrix matr = new Matrix(4);

        for (int y = 0; y < matr.size(); y++) {
            for (int x = 0; x < matr.size() - 1; x++) {
                matr.set(x, y, magic(x, y));
            }
            // save the number of column in the last row
            matr.set(matr.size() - 1, y, y);
        }

        matr.sortColumnsByFirstElement();

        for (int y = 0; y < matr.size(); y++) {
            // assert the sorted order
            if (y != 0) {
                assertTrue(matr.get(0, y - 1) <= matr.get(0, y));
            }
            // assert that columns are not corrupted
            for (int x = 0; x < matr.size() - 1; x++) {
                assertEquals(matr.get(x, y), magic(x, matr.get(matr.size() - 1, y)));
            }
        }
    }

    @Test
    public void get() throws Exception {
        Matrix matr = new Matrix(12, 7);
        for (int x = 0; x < matr.size(); x++) {
            for (int y = 0; y < matr.size(); y++) {
                matr.set(x, y, magic(x, y));
            }
        }
        for (int x = 0; x < matr.size(); x++) {
            for (int y = 0; y < matr.size(); y++) {
                assertEquals(matr.get(x, y), magic(x, y));
            }
        }
    }
}
