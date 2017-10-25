package api_method;

import com.google.gson.JsonElement;

public class MessageGetLongPollServer extends AbstractApiMethod {

    private final String METHOD_NAME = "messages.getLongPollServer";
    private final String LP_VERSION = "lp_version";
    private final String lpVersion = "2";

    /**
     * An empty constructor
     */
    public MessageGetLongPollServer() {
    }

    /**
     * Parameters: no
     * Returns an array of 3 strings as follows:
     * key, server, ts
     * This is the parameters required for requests to Long Poll Server
     *
     * @return
     */
    public String[] getLongPollServer() {

        //Clear the list of parameters for order's sake
        wipeParam();

        //for this API method only one parameter is required
        //a long poll version number
        setParam(LP_VERSION, this.lpVersion);

        //send GET-request and receive a JSON answer
        JsonElement jsonHttpResponse = formUrlAndSend(METHOD_NAME);

        //get the "response" part of JSON
        JsonElement jsonResponse = jsonHttpResponse.getAsJsonObject().get("response");

        //get the "key" parameter
        String key = jsonResponse.getAsJsonObject().get("key").toString();
        //cut opening and closing \"
        key = key.substring(1, (key.length() - 1));

        //get the "server" parameter
        String server = jsonResponse.getAsJsonObject().get("server").toString();
        //cut opening and closing \"
        server = server.substring(1, (server.length() - 1));

        //finally, get the "ts" parameter
        String ts = jsonResponse.getAsJsonObject().get("ts").toString();

        //a strings array to be returned
        String[] str = {key, server, ts};

        return str;

    }

}
