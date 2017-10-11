import api_method.MessageGetHistory;
import api_method.MessageSend;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class main_class {

    public static void main(String[] args){

        String localString = "";

        MessageSend messageSend = new MessageSend("6", "default_test");

        MessageGetHistory messageGetHistory = new MessageGetHistory("6");

        messageSend.setChatId("6");
        messageSend.setMessage("Бот_запущен.");
        messageSend.Send();

        while (true) {

            localString = messageGetHistory.getHistory();
            System.out.println(localString);


            if (Objects.equals("привет бот", localString)) {
                messageSend.setChatId("6");
                messageSend.setMessage("Привет,_кожаный_ублюдок!");
                messageSend.Send();
            }


            try {
                TimeUnit.MILLISECONDS.sleep(400L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

}

