package com.mynotebookbot.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SimpleBot extends TelegramLongPollingBot {

        static String state = "";
        String welcomeMsg;
        static int welcome = 0;

    //bot registration (the entering point to the entire application
	// a number of initial commands to start a bot
	public static void main(String[] args) {
        ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new SimpleBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	//name and token of your bot taken from Telegram
	@Override
	public String getBotUsername() {
		return "MySimpleNoteBookBot";
	}
 
	@Override
	public String getBotToken() {
		return "778916765:AAHw3mitlbSPE3XNX2AvGCZogwoeMioBEzw";
	}
 
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {
                        if(message.getText().equals("/start") ){
                            welcomeMsg = (welcome > 0) ? "Список доступных команд: \n" : "Добро пожаловать на страницу бота записной книжки.\n";
                            welcome++;
                            String str = Botcontrol.getInitCommand();
                            state = "empty";
                            sendMsg(message, welcomeMsg+str);
                       	}else{
                        	if(state!=null && !state.equals("") && state.length() > 0 && message.getText().length()>0){
								 String request = Botcontrol.getCommand(message.getText(), state);
                                sendMsg(message, request);
								 //state = Botcontrol.getState();
                        	}else {
                                sendMsg(message, "Для запуска бота наберите команду /start");
                        	}
                        }

		}
	}

	//send msg - the main method providing an interaction between the User and Bot
	//it create the new Message and get Chat ID as well as message ID.
	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(text);
		try{
		     execute(sendMessage);
		}catch(TelegramApiException e){
		     e.printStackTrace();
		}
	}
 
}
