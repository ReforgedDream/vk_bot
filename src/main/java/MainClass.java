import api_method.MessageSend;
import utils.LongPollParser;

import java.util.Objects;

public class MainClass {

    public static void main(String[] args) {

        LongPollParser lpParser = new LongPollParser();
        LongPoll lp = new LongPoll();
        MessageSend messageSend = new MessageSend("6", "default_test", false);

        String[] messageData;

        while (true) {

            messageData = lpParser.parseLongPollAnswer(lp.sendRequestToLongPoll());

            if (Objects.equals(messageData[0], "-1")) {

                System.out.println("Nothing has been parsed");

            } else {
                if (Objects.equals(messageData[1], "-1")) {

                    System.out.println("Error");

                } else {

                    System.out.println("ID: " + messageData[0]);
                    System.out.println("isChat: " + messageData[1]);
                    System.out.println("Flags: " + messageData[2]);
                    System.out.println("Message: " + messageData[3]);
                    /*
                    messageSend.setId(messageData[0]);
                    messageSend.setMessage("ID:%20" + messageData[0] + ",%20isChat:%20" + messageData[1] + ",%20flags:%20" + messageData[2]);
                    messageSend.setIsChat(Boolean.parseBoolean(messageData[1]));
                    messageSend.Send();
                    */
                }
            }
        }
    }
}

