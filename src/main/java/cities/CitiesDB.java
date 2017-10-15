package cities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

class CitiesDB {
    private HashSet<String> usedCities = new HashSet<>(); //Уже использованные города
    private ArrayList<String> citiesList = new ArrayList<>(); //Список всех городов

    CitiesDB() {
        try {
            addCities(); //Инициализируем города
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HashSet<String> getUsedCities() {
        return usedCities;
    }

    void setUsedCities(String usedCities) {
        this.usedCities.add(usedCities);
    }

    ArrayList<String> getСitiesList() {
        return citiesList;
    }

    private void addCities() throws IOException {
        String filePath = new File("").getAbsolutePath(); //Получить путь к текущему каталогу
        try {
            BufferedReader readFromFile = new BufferedReader(new FileReader(filePath.concat("/src/main/java/cities/CitiesOfRussia.txt")));
            for (String lineCity = readFromFile.readLine(); lineCity != null; lineCity = readFromFile.readLine()) {
                citiesList.add(lineCity.toLowerCase());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
