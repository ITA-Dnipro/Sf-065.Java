package com.ssjavaacademy.www.messengerattachments.mappers;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("messageMapper")
public class MessageMapper {
    public Message messagePostDtoToMessage(MessagePostDto messagePostDto) {
        Message message = new Message();

        message.setText(messagePostDto.getText());
        message.setSubject(messagePostDto.getSubject());
        message.setToUsers(messagePostDto.getToUsers());
        List<File> fileSet = new ArrayList<>();
        message.setFiles(fileSet);

        return message;
    }

    public static Message messageGetDtoToMessage(MessageGetDto messageGetDto) throws IOException {
        Message message = new Message();
        message.setText(messageGetDto.getText());
        message.setSubject(messageGetDto.getSubject());
        message.setFromUser(messageGetDto.getFromUser());
        message.setToUsers(messageGetDto.getToUsers());

        return message;
    }

    public static MessageGetDto messageToMessageGetDto(Message message) {
        MessageGetDto messageGetDto = new MessageGetDto();

        messageGetDto.setMessageId(message.getMessageId());
        messageGetDto.setText(message.getText());
        messageGetDto.setSubject(message.getSubject());
        messageGetDto.setFromUser(message.getFromUser());
        messageGetDto.setToUsers(message.getToUsers());
        messageGetDto.setCreatedAt(message.getCreatedAt());
        messageGetDto.setFiles(FileMapper.fileSetToFileGetSlimDtoSet(message.getFiles()));
        messageGetDto.setRead(message.getRead());

        return messageGetDto;
    }

    public List<MessageGetDto> messagesToMessageGetDtoList(List<Message> messages) {
        List<MessageGetDto> res = new ArrayList<>();

        for (Message m : messages) {
            res.add(messageToMessageGetDto(m));
        }

        return res;
    }
}
