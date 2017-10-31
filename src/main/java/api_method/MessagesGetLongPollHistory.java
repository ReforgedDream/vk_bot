package api_method;

import com.google.gson.JsonElement;

public class MessagesGetLongPollHistory extends AbstractApiMethod {

    private final String METHOD_NAME = "messages.getLongPollHistory";
    private final String LP_VERSION = "lp_version";
    private final String TS = "ts";

    private final String lp_version = "2";

    public void getLongPollHistory() {

        MessageGetLongPollServer getLongPollServer = new MessageGetLongPollServer();

        String[] strLpServerArray = getLongPollServer.getLongPollServer();

        wipeParam();

        setParam(TS, strLpServerArray[2]);
        setParam(LP_VERSION, lp_version);

        JsonElement jsonHttpResponse = formUrlAndSend(METHOD_NAME);
        JsonElement jsonResponse = jsonHttpResponse.getAsJsonObject().get("response");
        JsonElement jsonHistory = jsonResponse.getAsJsonObject().get("history");
        JsonElement jsonMessages = jsonResponse.getAsJsonObject().get("messages");

        System.out.println("History field:");
        System.out.println(jsonHistory.toString());
        System.out.println("Messages field:");
        System.out.println(jsonMessages.toString());

        System.out.println("The whole response:");
        System.out.println(jsonResponse.toString());
    }

}
