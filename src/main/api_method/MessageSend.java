package main.api_method;

public class MessageSend extends AbstractApiMethod {

    private final String METHOD_NAME = "messages.send";

    private final String CHAT_ID = "chat_id";

    private final String MESSAGE = "message";

    private String chatId;
    private String message;

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

        super.Send(API_URL + METHOD_NAME + "?" + getParamsToString());


    }


}
