package com.mynotebookbot.bot;

class Botcontrol{
    //not sure it was good idea to create one more class.
    //I think in the case of several coexisting bots that could make sense.
    //It is for purpose to separate the bot itself from its functionality and commands
    static String getCommand(String msgTxt, String state){
              return Commands.getCommand(msgTxt, state);
    }
    static String getInitCommand(){
              return Commands.getInitCommand();
     }
    static String getState(){
        return Commands.getCurrentState();
    }

}
