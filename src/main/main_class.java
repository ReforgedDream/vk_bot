package main;

import main.api_method.MessageSend;


public class main_class {

    public static void main(String[] args){

        MessageSend messageSend = new MessageSend();

        messageSend.setChatId("6");
        messageSend.setMessage("setter_test");
        messageSend.Send();

    }

}

