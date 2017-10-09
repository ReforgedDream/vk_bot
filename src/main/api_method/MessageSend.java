package main.api_method;

public class MessageSend extends AbstractApiMethod {

    private final String METHOD_NAME = "messages.send";

    private final String CHAT_ID = "chat_id";

    private final String MESSAGE = "message";

    private String chatId;
    private String message;

    public void setChatId(String chatId) {

        this.chatId = chatId;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * это конструктор
     *
     * @param chatId  айди чата
     * @param message сообщение для отправки
     */
    public MessageSend(String chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public void Send() {

        setParam(CHAT_ID, this.chatId);
        setParam(MESSAGE, this.message);

        StringBuilder sb2 = new StringBuilder();

        sb2.append(API_URL);
        sb2.append(METHOD_NAME);
        sb2.append("?");
        sb2.append(getParamsToString());

        super.Send(sb2.toString());

    }

}
