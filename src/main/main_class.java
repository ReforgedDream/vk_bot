package main;

import main.api_method.MessageSend;




public class main_class {

    public static void main(String[] args){

        new MessageSend("6", "test1").Send();
        new MessageSend("6", "test2").Send();
        new MessageSend("6", "test3").Send();


    }

}

