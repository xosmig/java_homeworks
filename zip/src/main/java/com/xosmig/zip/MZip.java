package com.xosmig.zip;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MZip {
    /**
     * If source is a directory, finds all .zip files in it
     * and extracts all files from them which match the pattern.
     *
     * <p>If source is a zip file, extracts all files from them which match the pattern.
     *
     * <p>Path translation:
     * <br><code>source/path/to/file.zip/path/inside/zip/file</code>
     * <br>becomes
     * <br><code>target/path/to/file.zip.extracted/path/inside/zip/file</code>
     */
    public static void extractMatch(Path source, Path target, Pattern pattern) throws IOException {
        if (!Files.exists(source)) {
            throw new IOException("\"" + source + "\" not found.");  // default message isn't that informative.
        }

        Files.walkFileTree(source, new UnzipVisitor(source, target, pattern));
    }

    /**
     * An implementation of FileVisitor for <code>extractMatch</code> method
     */
    private static final class UnzipVisitor extends SimpleFileVisitor<Path> {
        final Path source;
        final Path target;
        final Pattern pattern;

        public UnzipVisitor(Path source, Path target, Pattern pattern) {
            this.pattern = pattern;
            this.source = source;
            this.target = target;
        }

        @Override
        public FileVisitResult visitFile(Path zipFile, BasicFileAttributes attrs) throws IOException {
            if (zipFile.toString().toLowerCase().matches(".*\\.zip$")) {
                // source/path/to/file.zip --> target/path/to/file.zip.extracted
                Path targetDir = target.resolve(source.relativize(zipFile).toString() + ".extracted");

                try (
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFile.toFile()));
                        ZipInputStream zis = new ZipInputStream(bis)
                ) {
                    ZipEntry entry;
                    while ((entry = zis.getNextEntry()) != null) {
                        String name = entry.getName();
                        if (!entry.isDirectory() && pattern.matcher(name).matches()) {
                            Path file = Paths.get(entry.getName());
                            Path extractDir = targetDir;

                            // Add sub-directories from zip file to the path
                            Path dir = file.getParent();
                            if (dir != null) {
                                extractDir = extractDir.resolve(dir);
                            }

                            // extract file
                            Files.createDirectories(extractDir);
                            Path extractedFile = extractDir.resolve(file.getFileName());
                            extractZipEntry(zis, extractedFile);

                            System.out.println("file \"" + entry.getName() + "\" extracted");
                        }
                    }
                }
            }

            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * Takes <code>ZipInputStream</code> with currently opened
     * <code>ZipEntry</code> and extracts it to the <code>target</code>
     */
    private static void extractZipEntry(ZipInputStream zis, Path target) throws IOException {
        try (
            OutputStream fos = Files.newOutputStream(target)
        ) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = zis.read()) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
