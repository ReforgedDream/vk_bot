import cities.Cities;
import utils.LongPoll;
import utils.LongPollParser;

import java.util.Objects;

public class MainClass {

    public static void main(String[] args) {

        LongPollParser lpParser = new LongPollParser();
        LongPoll lp = new LongPoll();

        String[] messageData;
        boolean isInGame = false;

        while (!isInGame) {

            messageData = lpParser.parseLongPollAnswer(lp.sendRequestToLongPoll());

            if (!(Objects.equals(messageData[0], "-1")) && !(Objects.equals(messageData[1], "-1"))) {

                if (Objects.equals(messageData[3], "начать игру")) {

                    Cities cities = new Cities(messageData[0], messageData[1]);

                    cities.startGameCities();

                    isInGame = true;
                }
            }
        }

        //System.out.println("ID: " + messageData[0]);
        //System.out.println("isChat: " + messageData[1]);
        //System.out.println("Flags: " + messageData[2]);
        //System.out.println("Message: " + messageData[3]);
    }
}

