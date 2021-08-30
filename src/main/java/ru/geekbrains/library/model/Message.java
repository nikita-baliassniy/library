package ru.geekbrains.library.model;

import lombok.Data;

@Data
public class Message {
    private String sender;
    private String senderAddress;
    private String text;

    @Override
    public String toString() {
        return String.format("Имя: %s;%nАдрес: %s;%nТекст: %s", sender, senderAddress, text);
    }
}
