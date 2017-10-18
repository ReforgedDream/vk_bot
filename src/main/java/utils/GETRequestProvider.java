package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETRequestProvider {

    private final String USER_AGENT = "Mozilla/5.0";

    private String url;
    private int timeout = 0;

    /**
     * @param url, timeout
     */
    public GETRequestProvider(String url, int timeout) {

        this.timeout = timeout;
        this.url = url;
    }

    // HTTP GET request
    public JsonElement sendGet() throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setReadTimeout(this.timeout);

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\n[Util GETRequestProvider]Sending 'GET' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        //get the response from server
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JsonParser jsonParser = new JsonParser();

        //parse the response as JSON
        JsonElement jsonElement = jsonParser.parse(response.toString());

        return jsonElement;

    }

}
