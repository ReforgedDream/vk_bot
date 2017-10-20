package api_method;

import com.google.gson.JsonElement;

public class MessageSend extends AbstractApiMethod {

    private final String METHOD_NAME = "messages.send";
    private final String MESSAGE = "message";

    private boolean isChat = false;
    private String idParameter = null;
    private String id;
    private String message;

    /**
     * это конструктор
     *
     * @param id  айди чата
     * @param message сообщение для отправки
     */
    public MessageSend(String id, String message, boolean isChat) {
        this.id = id;
        this.message = message;
        this.isChat = isChat;

    }

    public void setMessage(String message) {

        this.message = message;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setIsChat(boolean isChat) {

        this.isChat = isChat;
    }

    public void Send() {

        vipeParam();

        if (isChat) {
            idParameter = "chat_id";
        } else {
            idParameter = "user_id";
        }

        setParam(idParameter, this.id);
        setParam(MESSAGE, "[Бот]" + this.message);

        StringBuilder sb2 = new StringBuilder();

        sb2.append(API_URL);
        sb2.append(METHOD_NAME);
        sb2.append("?");
        sb2.append(getParamsToString());

        JsonElement jsonElementObject = super.Send(sb2.toString());

    }

}
