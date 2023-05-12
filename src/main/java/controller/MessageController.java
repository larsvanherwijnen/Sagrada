package main.java.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.java.model.Game;
import main.java.model.Message;
import main.java.model.Player;

public class MessageController {

    private ViewController view;
    private String prefTimestamp;

    public MessageController(final ViewController view) {
        this.view = view;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.prefTimestamp = currentDateTime.format(formatter);
    }

    public boolean sendMessage(final String message, final ViewController view, final Game game) {
        boolean messageSent = false;
        String username = view.getAccountController().getAccount().getUsername();
        Player player = game.getPlayer(username);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        if (!this.prefTimestamp.equals(formattedDateTime)) {
            messageSent = Message.createMessage(message, player, formattedDateTime);
        }

        this.prefTimestamp = formattedDateTime;
        return messageSent;
    }
}