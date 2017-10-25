package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;

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
    public HashMap<String, String> parseLongPollAnswer(JsonElement inputJson) {

        //this string goes in "id" entry of the HashMap...
        //...when nothing has been parsed during this Long Poll "session"
        final String ID_ERROR_FLAG = "-1";
        //this string goes in "isChat" entry of the HashMap...
        //...when the input object is not a JSON Array...
        //...(in other words, when the protocol has been broken)
        final String IS_CHAT_ERROR_FLAG = "-1";

        HashMap<String, String> result = new HashMap<String, String>();
        result.clear();

        int sizeOfJsonArray = 0;
        int id = 0;

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

                                    result.put("id", Integer.toString(id));
                                    result.put("isChat", Boolean.toString(isChat));
                                    result.put("flags", jsonField.get(2).getAsString());
                                    result.put("message", jsonField.get(5).getAsString().toLowerCase());
                                    result.put("messageId", jsonField.get(1).getAsString());

                                    return result;
                                }

                            }
                        }
                    }
                }
            }

            //nothing has been parsed during this Long Poll "session"
            result.put("id", ID_ERROR_FLAG);
            return result;
        } else {

            //The input object is not a JSON Array
            result.put("isChat", IS_CHAT_ERROR_FLAG);
            return result;
        }

    }


}
