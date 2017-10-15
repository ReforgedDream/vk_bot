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
        if (lastStep != null) {
            for (int i = 1; i <= lastStep.length(); i++) {
                Character lastChar = lastStep.charAt(lastStep.length() - i);
                if (checkValidLastChar(lastChar)) {
                    return lastChar;
                }
            }
        }
        return null;
    }

    Boolean checkLastChar(String newStep) { //Проверка начальной буквы города
        if (lastStep != null) {
            Character lastChar = getLastChar();
            return lastChar != newStep.charAt(0);
        } else return false;
    }

    Boolean checkUsedCity(String newStep) { //Проверка использования города
        return citiesDB.getUsedCities().contains(newStep);
    }

    Boolean checkCity(String newStep) { //Проверка существования города
        return !citiesDB.getСitiesList().contains(newStep);
    }

    private Boolean checkValidLastChar(Character lastChar) { //Существует ли город на последнюю букву
        for (String entry : citiesDB.getСitiesList()) {
            if (entry.charAt(0) == lastChar) {
                return true;
            }
        }
        return false;
    }
}
