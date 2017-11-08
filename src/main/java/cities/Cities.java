package cities;

import api_method.MessageSend;
import utils.LongPoll;
import utils.LongPollParser;

import java.util.HashMap;
import java.util.Objects;

public class Cities {
    LongPollParser lpParser = new LongPollParser();
    private CitiesDB citiesDB = new CitiesDB("CitiesOfRussiaExtended.txt");
    private CitiesChecks citiesChecks = new CitiesChecks(citiesDB);
    LongPoll lp = new LongPoll();
    MessageSend messageSend = new MessageSend(null, null, false);

    private final String INVITATION = "Введите название города:";
    private final String PLEASE_ENTER_LETTER = "Введите название города на букву ";

    private final String ID_ERROR_FLAG = "-1";
    private final String IS_CHAT_ERROR_FLAG = "-1";

    private String userId = null;
    private String isChat = null;

    private HashMap<String, String> messageData = new HashMap<String, String>();

    /**
     * @param userId
     * @param isChat
     */
    public Cities(String userId, String isChat) {

        this.userId = userId;
        this.isChat = isChat;
    }

    public void startGameCities() { //Запуск игры "Города"
        final String TITLE = "Начнем игру!";

        messageSend.setId(this.userId);
        messageSend.setIsChat(Boolean.parseBoolean(this.isChat));
        messageSend.setMessage(TITLE + "<br>" + INVITATION);
        messageSend.Send();

        //noinspection StatementWithEmptyBody
        for (; userStep() && botStep(); ) {
        }
    }

    private Boolean userStep() { //Ход пользователя

        String stepId = null;
        String stepIsChat = null;
        String stepFlags = null;
        String stepMessage = null;
        String stepMessageId = null;

        for (int i = 0; i < 10; i++) {

            //Make a request to Long Poll server
            //and get a message data
            do {
                messageData = lpParser.parseLongPollAnswer(lp.sendRequestToLongPoll());

                for (String key : messageData.keySet()) {

                    switch (key) {
                        case "id":
                            stepId = messageData.get(key);
                            break;

                        case "isChat":
                            stepIsChat = messageData.get(key);
                            break;

                        case "flags":
                            stepFlags = messageData.get(key);
                            break;

                        case "message":
                            stepMessage = messageData.get(key);
                            break;

                        case "messageId":
                            stepMessageId = messageData.get(key);
                            break;

                        default:
                            break;
                    }
                }
                //if stepId == -1 then nothing has been parsed during this Long Poll "session"
                //if isChat == -1 then the received object is not a JSON Array...
                //...what implies that the JSON scheme is somehow broken
            } while (Objects.equals(stepId, ID_ERROR_FLAG) ||
                    Objects.equals(stepIsChat, IS_CHAT_ERROR_FLAG));

            if (stepMessage != null) {
                if (stepMessage.trim().length() == 0) { //Проверка корректного ввоода пользователя
                    continue;
                } else if (citiesChecks.checkLastChar(stepMessage)) { //Проверка начальной буквы города
                    messageSend.setMessage("Ошибка ввода, имя города должно начинаться на букву " + citiesChecks.getLastChar() + "!");
                    messageSend.Send();
                    continue;
                } else if (citiesChecks.checkUsedCity(stepMessage)) { //Проверка использования города
                    messageSend.setMessage("Город " + stepMessage + " уже использовался в игре!" + "<br>" +
                            PLEASE_ENTER_LETTER + citiesChecks.getLastChar() + ":");
                    messageSend.Send();
                    continue;
                } else if (citiesChecks.checkCity(stepMessage)) { //Проверка существования города
                    String noSuchCity = "Город " + stepMessage + " не существует!";
                    if (citiesChecks.getLastChar() == null) {
                        messageSend.setMessage(noSuchCity + "<br>" + INVITATION);
                    } else {
                        messageSend.setMessage(noSuchCity + "<br>" + PLEASE_ENTER_LETTER + citiesChecks.getLastChar() + ":");
                    }

                    messageSend.Send();
                    continue;
                }
            }
            citiesDB.setUsedCities(stepMessage); //Добавить последний ход в базу использованных городов
            citiesChecks.setLastStep(stepMessage); //Запомнить последний ход

            return true; //для продолжения игры
        }
        messageSend.setMessage("Вы исчерпали количество попыток! Игра окончена.");
        messageSend.Send();
        return false;
    }

    private Boolean botStep() { //Ход бота
        int i = 0; //Счетчик для определения последнего цикла
        for (String entry : citiesDB.getСitiesList()) {
            if (entry.charAt(0) == citiesChecks.getLastChar() && !citiesChecks.checkUsedCity(entry)) {
                citiesDB.getUsedCities().add(entry); //Добавить последний ход в базу использованных городов
                citiesChecks.setLastStep(entry); //Запомнить последний ход
                messageSend.setMessage("Успешно! Ход противника: " +
                        Character.toUpperCase(entry.charAt(0)) + entry.substring(1) +
                        ". " + PLEASE_ENTER_LETTER + Character.toUpperCase(citiesChecks.getLastChar()) + ":");
                messageSend.Send();
                break; //для продолжения игры
            } else if (i++ == citiesDB.getСitiesList().size() - 1) { //Последний ход
                messageSend.setMessage("Бот не может назвать город! Вы победили!");
                messageSend.Send();
                return false;
            }
        }
        return true;
    }
}
