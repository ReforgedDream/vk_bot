package cities;

import java.util.ArrayList;
import java.util.HashSet;

class CitiesDB {
    private HashSet<String> usedCities = new HashSet<>(); //Уже использованные города
    private ArrayList<String> citiesList = new ArrayList<>(); //Список всех городов

    {
        addCities(); //Инициализируем города
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

    private void addCities() {
        citiesList.add("москва");
        citiesList.add("абакан");
        citiesList.add("новосибирск");
        citiesList.add("кемерово");
        citiesList.add("омск");
        citiesList.add("керчь");
    }
}