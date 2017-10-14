package cities;

import java.util.ArrayList;
import java.util.HashSet;

public class CitiesDB {
    private HashSet<String> usedCities = new HashSet<>(); //Уже использованные города
    private ArrayList<String> citiesList = new ArrayList<>(); //Список всех городов
    private Character lastChar;
    private String botLastStep;

    {
        addCities(); //Инициализируем города
    }

    public String getBotLastStep() {
        return botLastStep;
    }

    private void addCities() {
        citiesList.add("москва");
        citiesList.add("абакан");
        citiesList.add("новосибирск");
        citiesList.add("кемерово");
        citiesList.add("омск");
        citiesList.add("керчь");
    }

    public Boolean isContain(String newStep, String lastStep) {
        if (lastChar != null) {
            lastChar = lastStep.charAt(lastStep.length() - 1); //Последняя буква из предыдущего хода

            if (newStep.charAt(0) != lastChar) {
                System.out.print("Ошибка ввода, имя города должно начинаться на " + lastChar + "! ");
                return false;
            }
        }

        if (usedCities.contains(newStep)) {
            System.out.println("Город уже назывался, повторите попытку :");
            return false;
        } else if (citiesList.contains(newStep)) {
            usedCities.add(newStep);
            return true;
        } else {
            System.out.print("Город не найден! ");
            return false;
        }
    }

    public Boolean botAnswer(String lastStep) {
        lastChar = lastStep.charAt(lastStep.length() - 1); //Последняя буква из предыдущего хода

        for (String entry : citiesList) {
            if (entry.charAt(0) == lastChar) {
                if (usedCities.contains(entry)) {
                    continue;
                } else {
                    System.out.println("Бот говорит: ");
                    System.out.println(entry);
                    usedCities.add(entry);
                    botLastStep = entry;
                    return true;
                }
            } else {
                continue;
            }
        }
        return false;
    }
}