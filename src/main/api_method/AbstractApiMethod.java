package main.api_method;

import main.utils.FileReader;
import main.utils.GETRequestProvider;

import java.util.HashMap;

public abstract class AbstractApiMethod {

    protected final String API_URL = "https://api.vk.com/method/";

    private final String TOKEN_STRING = "access_token";

    private final String TOKEN_API = new FileReader("src/main/token").ReadFile();

    private HashMap<String, String> params = new HashMap<String, String>();

    public void setParam(String key, String value) {
        this.params.put(key, value);
    }

    public String getParamsToString() {

        if (this.params.isEmpty()) {
            return "";
        }

        String output = "";

        for (String key : params.keySet()) {

            output += key + "=" + params.get(key) + "&";

        }

        return output + TOKEN_STRING + "=" + TOKEN_API;

    }

    public void Send(String url) {

        try {
            new GETRequestProvider(url).sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
