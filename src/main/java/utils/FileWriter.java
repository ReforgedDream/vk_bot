package utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileWriter {

    private static FileWriter instance;

    public static synchronized FileWriter getInstance() {
        if (instance == null) {
            instance = new FileWriter();
        }
        return instance;
    }

    /**
     * Append the string to log file
     * If the file doesn't exist, create, otherwise append to it
     *
     * @param lineToWrite - a String that would be added to file,
     *                    preceded with current date and time
     */
    public void writeToFile(String lineToWrite) {

        // hide any secret keys or tokens
        // and convert the string to a byte array
        byte data[] = lineToWrite.replaceAll("(access_token=[a-fA-F0-9]+)|(key=[a-fA-F0-9]+)",
                "[HIDDEN]").getBytes();

        //Specified date and time format, for file name
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //A path to file
        String fileName = "LOG " + LocalDateTime.now().format(FORMATTER).toString() + ".txt";

        Path file = Paths.get(fileName);

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE, APPEND));) {
            //write current time
            out.write(LocalDateTime.now().toString().getBytes());
            //append whitespace
            out.write('\u0020');
            //append the input string
            out.write(data, 0, data.length);
            //append Carriage Return and Line Feed
            out.write("\r\n".getBytes());
            //append Carriage Return and Line Feed
            out.write("\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
