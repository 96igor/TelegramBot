package job.Project29.service;


import job.Project29.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String massgeText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (massgeText) {
                case "/start":
                    try {
                        startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                default:
                    try {
                        sendMassage(chatId, "Sorry, command was not recognized");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
            }
        }

    }

    private void startCommandRecived(long chatId, String name) throws TelegramApiException {

        String answer = "Hi, " + name + ", nice to meet you!";

        sendMassage(chatId, answer);

    }

    private void sendMassage(long chatId, String textToSend) throws TelegramApiException {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {


        }
    }
}
