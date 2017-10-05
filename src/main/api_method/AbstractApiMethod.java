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

        StringBuilder sb1 = new StringBuilder();

        for (String key : params.keySet()) {

            sb1.append(key);
            sb1.append("=");
            sb1.append(params.get(key));
            sb1.append("&");
        }

        sb1.append(TOKEN_STRING);
        sb1.append("=");
        sb1.append(TOKEN_API);

        return sb1.toString();

    }

    public void Send(String url) {

        try {
            new GETRequestProvider(url).sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
