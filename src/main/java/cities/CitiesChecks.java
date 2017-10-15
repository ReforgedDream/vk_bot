package cities;

class CitiesChecks {
    private CitiesDB citiesDB = new CitiesDB();
    private String lastStep;

    void setLastStep(String lastStep) {
        this.lastStep = lastStep;
    }

    CitiesDB getCitiesDB() {
        return citiesDB;
    }

    Character getLastChar() {
        return lastStep.charAt(lastStep.length() - 1);
    }

    Boolean checkLastChar(String newStep) { //Проверка начальной буквы города
        if (lastStep != null) {
            Character lastChar = lastStep.charAt(lastStep.length() - 1);
            return lastChar != newStep.charAt(0);
        } else return false;
    }

    Boolean checkUsedCity(String newStep) { //Проверка использования города
        return citiesDB.getUsedCities().contains(newStep);
    }

    Boolean checkCity(String newStep) { //Проверка существования города
        return !citiesDB.getСitiesList().contains(newStep);
    }

}
