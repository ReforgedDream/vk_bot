import api_method.MessageGetLongPollServer;
import com.google.gson.JsonElement;
import utils.GETRequestProvider;

public class LongPoll {

    //it's should be static because it's the global parameters
    private static boolean isFirstTime = true;
    private static String ts = null;

    private static String[] longpollServerParams = new String[3];

    /**
     * An empty constructor
     */
    public LongPoll() {
    }

    /**
     * Makes a request to Long Poll server
     * Args: null
     *
     * @return a Json element containing "updates" field from answer
     */
    public JsonElement sendRequestToLongPoll() {

        StringBuilder stringBuilder = new StringBuilder();

        JsonElement returnJson = new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }
        };

        //if it's the 1st time we accessing a Long Poll server...
        //...than we should first get certain parameters by API function...
        //...the server's address, initial TS and security key
        if (isFirstTime) {

            MessageGetLongPollServer getLongPollServer = new MessageGetLongPollServer();

            //getting the params by API function
            this.longpollServerParams = getLongPollServer.getLongPollServer();

            //getting the initial TS value
            this.ts = this.longpollServerParams[2];

            //clear this flag
            this.isFirstTime = false;

        }

        //construct the request to Long Poll
        stringBuilder.append("https://");
        //the address
        stringBuilder.append(this.longpollServerParams[1]);
        stringBuilder.append("?act=a_check&key=");
        //the security key
        stringBuilder.append(this.longpollServerParams[0]);
        stringBuilder.append("&ts=");
        //the TS parameter
        stringBuilder.append(this.ts);
        //timeout, mode and version
        stringBuilder.append("&wait=25&mode=2&version=2");

        //send GET request with rather a long timeout
        //the recommended timeout for requests to Long Poll is 25 seconds
        //so for GET request it should be somehow longer
        try {
            returnJson = new GETRequestProvider(stringBuilder.toString(), 30000).sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get a new TS value from received JSON...
        this.ts = returnJson.getAsJsonObject().get("ts").toString();
        //...and get the "updates" section
        JsonElement jsonUpdates = returnJson.getAsJsonObject().get("updates");

        return jsonUpdates;

    }

}
