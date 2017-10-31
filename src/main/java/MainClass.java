import cities.Cities;
import utils.LongPoll;
import utils.LongPollParser;

import java.util.HashMap;
import java.util.Objects;

public class MainClass {

    public static void main(String[] args) {

        final String ID_ERROR_FLAG = "-1";
        final String IS_CHAT_ERROR_FLAG = "-1";

        LongPollParser lpParser = new LongPollParser();
        LongPoll lp = new LongPoll();

        HashMap<String, String> messageData;

        boolean isInGame = false;

        while (!isInGame) {

            messageData = lpParser.parseLongPollAnswer(lp.sendRequestToLongPoll());

            if (!(Objects.equals(messageData.get("id"), ID_ERROR_FLAG)) &&
                    !(Objects.equals(messageData.get("isChat"), IS_CHAT_ERROR_FLAG))) {

                if (Objects.equals(messageData.get("message"), "начать игру")) {

                    Cities cities = new Cities(messageData.get("id"), messageData.get("isChat"));

                    cities.startGameCities();

                    isInGame = true;
                }
            }
        }
    }
}

