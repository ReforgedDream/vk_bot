package api_method;

import com.google.gson.JsonElement;
import utils.FileReader;
import utils.GETRequestProvider;

import java.util.HashMap;

public abstract class AbstractApiMethod {

    protected final String API_URL = "https://api.vk.com/method/";

    private final String TOKEN_STRING = "access_token";

    private final String TOKEN_API = new FileReader("src/main/java/token").ReadFile();

    private final String VERSION = "v";

    private final String version = "5.68";

    private HashMap<String, String> params = new HashMap<String, String>();

    public void setParam(String key, String value) {
        this.params.put(key, value);
    }

    public void vipeParam() {
        this.params.clear();
    }

    public String getParamsToString() {

        if (this.params.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {

            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(params.get(key));
            stringBuilder.append("&");
        }

        stringBuilder.append(TOKEN_STRING);
        stringBuilder.append("=");
        stringBuilder.append(TOKEN_API);

        stringBuilder.append("&");

        stringBuilder.append(VERSION);
        stringBuilder.append("=");
        stringBuilder.append(version);

        return stringBuilder.toString();

    }


    public JsonElement formUrlAndSend(String methodName) {
        StringBuilder sb2 = new StringBuilder();

        sb2.append(API_URL);
        sb2.append(methodName);
        sb2.append("?");
        sb2.append(getParamsToString());

        JsonElement jsonElementObject = Send(sb2.toString());

        return jsonElementObject;
    }



    public JsonElement Send(String url) {

        JsonElement returnJson = new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }
        };

        try {
            returnJson = new GETRequestProvider(url, 0).sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnJson;
    }

}
