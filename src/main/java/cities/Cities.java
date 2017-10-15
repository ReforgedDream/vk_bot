package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Cities {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String userInput;
    private CitiesChecks citiesChecks = new CitiesChecks();

    public void startGameCities() { //Запуск игры "Города"
        final String title = "Начнем игру!";
        System.out.println(title);

        for (Boolean isGameContinue = true; isGameContinue; ) {
            userStep();
            isGameContinue = botStep();
        }
    }

    private void userStep() { //Ход пользователя
        for (; true; ) {
            System.out.println("Введите название города :");
            try {
                userInput = reader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            if (userInput.trim().length() == 0) {
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
            break; //Выход из цикла, для продолжения игры
        }
    }

    private Boolean botStep() { //Ход бота
        int i = 0; //Счетчик для определения последнего цикла
        for (String entry : citiesChecks.getCitiesDB().getСitiesList()) {
            if (entry.charAt(0) == citiesChecks.getLastChar() && !citiesChecks.checkUsedCity(entry)) {
                System.out.println(entry);
                citiesChecks.getCitiesDB().getUsedCities().add(entry); //Добавить последний ход в базу использованных городов
                citiesChecks.setLastStep(entry); //Запомнить последний ход
                break; //Выход из цикла, для продолжения игры
            } else if (i++ == citiesChecks.getCitiesDB().getСitiesList().size() - 1) {
                System.out.println("Бот не может назвать город! Вы победили!");
                return false;
            }
        }
        return true;
    }
}




