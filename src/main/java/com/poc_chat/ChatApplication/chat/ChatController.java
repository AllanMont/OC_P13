package com.poc_chat.ChatApplication.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatEvent envoyerMessage(@Payload ChatEvent chatMessage) {
        return chatMessage; // Diffuse le message à tous les utilisateurs
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatEvent ajouterUtilisateur(@Payload ChatEvent chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Ajoute le nom d'utilisateur dans les attributs de la session WebSocket
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        // Crée un message pour signaler l'arrivée d'un nouvel utilisateur
        ChatEvent joinMessage = ChatEvent.builder()
                .content(chatMessage.getSender() + " a rejoint le chat !")
                .sender("System")
                .type(MessageEnum.JOIN)
                .build();

        return joinMessage; // Retourne ce message pour être diffusé
    }

    @MessageMapping("/chat.leaveUser")
    @SendTo("/topic/public")
    public ChatEvent quitterUtilisateur(@Payload ChatEvent chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Supprime l'utilisateur des attributs de session
        headerAccessor.getSessionAttributes().remove("username");

        // Crée un message pour signaler le départ d'un utilisateur
        ChatEvent leaveMessage = ChatEvent.builder()
                .content(chatMessage.getSender() + " a quitté le chat.")
                .sender("System")
                .type(MessageEnum.LEAVE)
                .build();

        return leaveMessage; // Retourne ce message pour être diffusé
    }
}
