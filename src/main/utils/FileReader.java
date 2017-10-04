package main.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    private String path;
    private Charset encoding = Charset.defaultCharset();

    public FileReader(String path) {

        this.path = path;

    }

    public String ReadFile() {
        byte[] encoded = null;

        try {

            encoded = Files.readAllBytes(Paths.get(this.path));


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(encoded, this.encoding);
    }

}
