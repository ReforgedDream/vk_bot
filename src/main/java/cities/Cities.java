package cities;

import api_method.MessageSend;
import utils.LongPoll;
import utils.LongPollParser;

import java.util.Objects;

public class Cities {
    LongPollParser lpParser = new LongPollParser();
    private CitiesDB citiesDB = new CitiesDB("CitiesOfRussiaExtended.txt");
    private CitiesChecks citiesChecks = new CitiesChecks(citiesDB);
    LongPoll lp = new LongPoll();
    MessageSend messageSend = new MessageSend(null, null, false);

    private final String invitation = "Введите название города:";
    private final String pleaseEnterLetter = "Введите название города на букву ";

    private String userId = null;
    private String isChat = null;

    private String[] messageData = new String[5];

    /**
     * @param userId
     * @param isChat
     */
    public Cities(String userId, String isChat) {

        this.userId = userId;
        this.isChat = isChat;
    }

    public void startGameCities() { //Запуск игры "Города"
        final String title = "Начнем игру!";

        messageSend.setId(this.userId);
        messageSend.setIsChat(Boolean.parseBoolean(this.isChat));
        messageSend.setMessage(title + "<br>" + invitation);
        messageSend.Send();

        //noinspection StatementWithEmptyBody
        for (; userStep() && botStep(); ) {
        }
    }

    private Boolean userStep() { //Ход пользователя

        String userInput = null;

        for (int i = 0; i < 10; i++) {


            //Make a request to Long Poll server
            //and get a message data
            do {
                messageData = lpParser.parseLongPollAnswer(lp.sendRequestToLongPoll());
            } while (Objects.equals(messageData[0], "-1") ||
                    Objects.equals(messageData[1], "-1"));

            userInput = messageData[3];

            if (userInput != null) {
                if (userInput.trim().length() == 0) { //Проверка корректного ввоода пользователя
                    continue;
                } else if (citiesChecks.checkLastChar(userInput)) { //Проверка начальной буквы города
                    messageSend.setMessage("Ошибка ввода, имя города должно начинаться на букву " + citiesChecks.getLastChar() + "!");
                    messageSend.Send();
                    continue;
                } else if (citiesChecks.checkUsedCity(userInput)) { //Проверка использования города
                    messageSend.setMessage("Город " + userInput + " уже использовался в игре!" + "<br>" +
                            pleaseEnterLetter + citiesChecks.getLastChar() + ":");
                    messageSend.Send();
                    continue;
                } else if (citiesChecks.checkCity(userInput)) { //Проверка существования города
                    String noSuchCity = "Город " + userInput + " не существует!";
                    if (citiesChecks.getLastChar() == null) {
                        messageSend.setMessage(noSuchCity + "<br>" + invitation);
                    } else {
                        messageSend.setMessage(noSuchCity + "<br>" + pleaseEnterLetter + citiesChecks.getLastChar() + ":");
                    }

                    messageSend.Send();
                    continue;
                }
            }
            citiesDB.setUsedCities(userInput); //Добавить последний ход в базу использованных городов
            citiesChecks.setLastStep(userInput); //Запомнить последний ход
            //messageSend.setMessage("Успешно! Ход противника:");
            //messageSend.Send();
            return true; //Выход из цикла, для продолжения игры
        }
        messageSend.setMessage("Вы исчерпали количество попыток! Игра окончена.");
        messageSend.Send();
        return false;
    }

    private Boolean botStep() { //Ход бота
        int i = 0; //Счетчик для определения последнего цикла
        for (String entry : citiesDB.getСitiesList()) {
            if (entry.charAt(0) == citiesChecks.getLastChar() && !citiesChecks.checkUsedCity(entry)) {
                messageSend.setMessage("Успешно! Ход противника: " + entry + ". " +
                        pleaseEnterLetter + entry.charAt(entry.length() - 1) + ":");
                messageSend.Send();
                citiesDB.getUsedCities().add(entry); //Добавить последний ход в базу использованных городов
                citiesChecks.setLastStep(entry); //Запомнить последний ход
                break; //Выход из цикла, для продолжения игры
            } else if (i++ == citiesDB.getСitiesList().size() - 1) { //Последний ход
                messageSend.setMessage("Бот не может назвать город! Вы победили!");
                messageSend.Send();
                return false;
            }
        }
        return true;
    }
}