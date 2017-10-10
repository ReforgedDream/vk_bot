

import api_method.MessageSend;

public class main_class {

    public static void main(String[] args){

        MessageSend messageSend = new MessageSend("6", "test");

        messageSend.setChatId("6");
        messageSend.setMessage("gradle_test");
        messageSend.Send();

    }

}

