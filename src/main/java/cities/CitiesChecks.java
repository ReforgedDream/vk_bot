package cities;

class CitiesChecks {
    private CitiesDB citiesDB = new CitiesDB();
    private String lastStep;

    void setLastStep(String lastStep) {
        this.lastStep = lastStep;
    }

    Character getLastChar() {
        return lastStep.charAt(lastStep.length() - 1);
    }

    Boolean CheckLastChar(String newStep) { //Проверка начальной буквы города
        if (lastStep != null) {
            Character lastChar = lastStep.charAt(lastStep.length() - 1);
            return lastChar != newStep.charAt(0);
        } else return false;
    }

    Boolean CheckUsedCity(String newStep) { //Проверка использования города
        return citiesDB.getUsedCities().contains(newStep);
    }

    Boolean CheckCity(String newStep) { //Проверка существования города
        return !citiesDB.getСitiesList().contains(newStep);
    }


}
