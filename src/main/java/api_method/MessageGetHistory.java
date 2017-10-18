package api_method;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class MessageGetHistory extends AbstractApiMethod {

    static int messageId = 0;
    private final String METHOD_NAME = "messages.getHistory";
    private final String CHAT_ID = "chat_id";
    private final String OFFSET = "offset";
    private final String COUNT = "count";
    private String chatId;
    private String offset = "0";
    private String count = "1";

    /**
     * @param chatId
     */
    public MessageGetHistory(String chatId) {
        this.chatId = chatId;
    }

    public String getHistory() {

        setParam(CHAT_ID, this.chatId);
        setParam(OFFSET, this.offset);
        setParam(COUNT, this.count);

        JsonElement jsonHttpResponse = formUrlAndSend(METHOD_NAME);

        JsonElement jsonResponse = jsonHttpResponse.getAsJsonObject().get("response");
        JsonArray jsonArray = jsonResponse.getAsJsonArray();
        JsonElement jsonFirstElement = jsonArray.get(1);

        String body = jsonFirstElement.getAsJsonObject().get("body").toString();
        body = body.substring(1, (body.length() - 1));
        int messageId = jsonFirstElement.getAsJsonObject().get("mid").getAsInt();


        if (this.messageId != messageId) {

            this.messageId = messageId;
            return body;
        } else {
            return null;
        }

    }
}
