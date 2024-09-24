package com.poc_chat.ChatApplication.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatEvent {
    private String content;
    private String sender;
    private MessageEnum type;
}
