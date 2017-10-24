package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class LongPollParser {

    /**
     * An empty constructor
     */
    public LongPollParser() {
    }

    /**
     * Parses the "updates" section from Long Poll server
     * which has to be a JSON Array
     *
     * @param inputJson
     */
    public String[] parseLongPollAnswer(JsonElement inputJson) {

        int sizeOfJsonArray = 0;
        int id = 0;
        String[] answer = new String[5];

        answer[0] = null;
        answer[1] = null;
        answer[2] = null;
        answer[3] = null;
        answer[4] = null;

        boolean isChat = false;

        //check if the input object is JSON Array
        if (inputJson.isJsonArray()) {

            sizeOfJsonArray = inputJson.getAsJsonArray().size();

            JsonArray jsonField;

            //iterate through all of the elements of the "updates" section
            //...which is array and contains arrays
            for (int i = 0; i < sizeOfJsonArray; i++) {

                //if an element is JSON Array...
                if (inputJson.getAsJsonArray().get(i).isJsonArray()) {

                    jsonField = inputJson.getAsJsonArray().get(i).getAsJsonArray();

                    //...then examine its first (number 0) element
                    //it should be primitive object
                    //if it equals 4 then it's an array with information regarding message
                    if (jsonField.get(0).isJsonPrimitive() &&
                            (jsonField.get(0).getAsInt() == 4)) {

                        //examine if the array's length is more or equals 5
                        if (jsonField.size() >= 5) {

                            //check if there's message, ID, flags and message id
                            if (jsonField.get(5).isJsonPrimitive() &&
                                    jsonField.get(3).isJsonPrimitive() &&
                                    jsonField.get(2).isJsonPrimitive() &&
                                    jsonField.get(1).isJsonPrimitive()) {

                                if (!jsonField.get(5).getAsString().contains("[Бот]")) {

                                    //get the flags and mask it by 16
                                    //16 is the mask for CHAT flag
                                    if ((jsonField.get(2).getAsInt() & 16) == 16) {
                                        isChat = true;
                                        //for messages from chats, this number is added to ID value
                                        id = jsonField.get(3).getAsInt() - 2000000000;
                                    } else {
                                        isChat = false;
                                        id = jsonField.get(3).getAsInt();
                                    }

                                    //return as strings: ID, isChat flag, flags from API, message and message ID
                                    answer[0] = Integer.toString(id);
                                    answer[1] = Boolean.toString(isChat);
                                    answer[2] = jsonField.get(2).getAsString();
                                    answer[3] = jsonField.get(5).getAsString().toLowerCase();
                                    answer[4] = jsonField.get(1).getAsString();
                                    return answer;
                                }

                            }
                        }
                    }
                }
            }

            //nothing has been parsed during this Long Poll "session"
            answer[0] = "-1";
            return answer;
        } else {

            //The input object is not a JSON Array
            answer[1] = "-1";
            return answer;
        }

    }


}
