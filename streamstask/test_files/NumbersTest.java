package com.xosmig.numbers;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NumbersTest {
    @Test
    public void taskTest() throws Exception {
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        Path dir = fs.getPath("/");

        Path input = dir.resolve("input.txt");
        Files.write(input, "12345 hello 321 not null 0 1 2".getBytes());

        Path output = dir.resolve("output.txt");
        Numbers.task(input, output);

        assertThat(Files.readAllBytes(output), equalTo("152399025 null 103041 null null 0 1 4 ".getBytes()));
    }
}