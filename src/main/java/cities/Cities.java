package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Cities {
    private Boolean isGameContinue = true;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String userInput;
    private CitiesDB citiesDB = new CitiesDB();
    private CitiesChecks citiesChecks = new CitiesChecks();

    public void StartGameCities() { //Запуск игры "Города"
        String title = "Начнем игру!";
        System.out.println(title);
        Cities cities = new Cities();

        for (; isGameContinue; ) {
            cities.UserStep();
            cities.BotStep();
        }
    }
    private void UserStep() { //Ход пользователя
        for (; true; ) {
            System.out.println("Введите название города :");
            try {
                userInput = reader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            if (userInput.trim().length() == 0) {
                continue;
            } else if (citiesChecks.CheckLastChar(userInput)) { //Проверка начальной буквы города
                System.out.println("Ошибка ввода, имя города должно начинаться на букву " + citiesChecks.getLastChar() + "!");
                continue;
            } else if (citiesChecks.CheckUsedCity(userInput)) { //Проверка использования города
                System.out.println("Город " + userInput + " уже использовался в игре!");
                continue;
            } else if (citiesChecks.CheckCity(userInput)) { //Проверка существования города
                System.out.println("Город " + userInput + " не существует!");
                continue;
            }
            citiesDB.setUsedCities(userInput); //Добавить последний ход в базу использованных городов
            citiesChecks.setLastStep(userInput); //Запомнить последний ход
            System.out.println("Успешно! Ход противника:");
            break; //Выход из цикла, для продолжения игры
        }
    }

    private void BotStep() { //Ход бота
        int i = 0; //Счетчик для определения последнего цикла
        for (String entry : citiesDB.getСitiesList()) {
            if (entry.charAt(0) == citiesChecks.getLastChar() && !citiesChecks.CheckUsedCity(entry)) {
                System.out.println(entry);
                citiesDB.getUsedCities().add(entry); //Добавить последний ход в базу использованных городов
                citiesChecks.setLastStep(entry); //Запомнить последний ход
                break; //Выход из цикла, для продолжения игры
            } else if (i++ == citiesDB.getСitiesList().size() - 1) {
                System.out.println("Бот не может назвать город! Вы победили!");
                isGameContinue = false;
            }
        }
    }
}




