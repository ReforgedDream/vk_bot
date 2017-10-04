package main;

import sun.text.normalizer.UTF16;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class main_class {




    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    static void sendGet() {

        String apiURL = "https://api.vk.com/method/";
        String apiMethod = "messages.send";
        String message = "lol";
        String domain = "dizik";
        String token = "0";

        try {
           token = readFile("src/main/token", Charset.defaultCharset());
        }
        catch (IOException e){
            e.printStackTrace(System.out);
        }

        String completeURL = apiURL + apiMethod + "?" + "domain=" + domain + "&" + "message=" + message + "&" + "access_token=" + token;

        System.out.println(completeURL);


    }





    public static void main(String[] args){

        sendGet();


    }
}
