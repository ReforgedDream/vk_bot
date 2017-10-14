package cities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Cities {
    private static String title = "Начнем игру! Ввведите название города :";
    private static Boolean isGameContinue = true;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String userInput;
    private String lastStep = null;
    private CitiesDB citiesDB = new CitiesDB();

    public void StartGameCities() {
        System.out.println(title);
        Cities cities = new Cities();

        for (; isGameContinue; ) {
            cities.UserStep();
            cities.BotStep();
        }
    }

    private void UserStep() { //Ход пользователя
        for (; true; ) {
            try {
                userInput = reader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println(e.toString());
            }

            if (citiesDB.isContain(userInput, lastStep)) {
                System.out.println("Город найден! Ход бота :");
                lastStep = userInput; //Запомнить последний ход пользователя
                break;
            } else {
                System.out.println("Повторите попытку :");
            }
        }
    }

    private void BotStep() { //Ход бота
        if (!citiesDB.botAnswer(lastStep)) {
            System.out.println("Бот не может назвать город! Вы победили!");
            isGameContinue = false;
        }
        lastStep = citiesDB.getBotLastStep(); //Запоминить послений ход бота
    }
}





