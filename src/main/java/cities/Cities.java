package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Cities {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String userInput;
    private CitiesChecks citiesChecks = new CitiesChecks();
    private Character lastChar = null;

    public void startGameCities() { //Запуск игры "Города"
        final String title = "Начнем игру!";
        System.out.println(title);

        for (Boolean isGameContinue = true; isGameContinue; ) {
            isGameContinue = userStep();
            if (isGameContinue) {
                isGameContinue = botStep();
            }
        }
    }

    private Boolean userStep() { //Ход пользователя
        lastChar = citiesChecks.getLastChar();
        for (int i = 0; i <= 10; i++) {
            if (lastChar != null) {
                System.out.println("Введите название города на букву " + citiesChecks.getLastChar() + " :");
            } else {
                System.out.println("Введите название города :");
            }

            try {
                userInput = reader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            if (i == 10) {
                System.out.println("Вы исчерпали количество попыток! Игра окончена.");
                return false;
            } else if (userInput.trim().length() == 0) { //Проверка корректного ввоода пользователя
                continue;
            } else if (citiesChecks.checkLastChar(userInput)) { //Проверка начальной буквы города
                System.out.println("Ошибка ввода, имя города должно начинаться на букву " + citiesChecks.getLastChar() + "!");
                continue;
            } else if (citiesChecks.checkUsedCity(userInput)) { //Проверка использования города
                System.out.println("Город " + userInput + " уже использовался в игре!");
                continue;
            } else if (citiesChecks.checkCity(userInput)) { //Проверка существования города
                System.out.println("Город " + userInput + " не существует!");
                continue;
            }
            citiesChecks.getCitiesDB().setUsedCities(userInput); //Добавить последний ход в базу использованных городов
            citiesChecks.setLastStep(userInput); //Запомнить последний ход
            System.out.println("Успешно! Ход противника:");
            return true; //Выход из цикла, для продолжения игры
        }
        return false;
    }

    private Boolean botStep() { //Ход бота
        int i = 0; //Счетчик для определения последнего цикла
        lastChar = citiesChecks.getLastChar();
        for (String entry : citiesChecks.getCitiesDB().getСitiesList()) {
            if (entry.charAt(0) == lastChar && !citiesChecks.checkUsedCity(entry)) {
                System.out.println(entry);
                citiesChecks.getCitiesDB().getUsedCities().add(entry); //Добавить последний ход в базу использованных городов
                citiesChecks.setLastStep(entry); //Запомнить последний ход
                break; //Выход из цикла, для продолжения игры
            } else if (i++ == citiesChecks.getCitiesDB().getСitiesList().size() - 1) { //Последний ход
                System.out.println("Бот не может назвать город! Вы победили!");
                return false;
            }
        }
        return true;
    }
}




