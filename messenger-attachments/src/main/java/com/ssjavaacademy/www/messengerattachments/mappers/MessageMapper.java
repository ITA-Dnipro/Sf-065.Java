package com.ssjavaacademy.www.messengerattachments.mappers;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.ssjavaacademy.www.messengerattachments.services.MessageService.setMessageFilesSet;

@Component("messageMapper")
public class MessageMapper {

    public Message messagePostDtoToMessage(MessagePostDto messagePostDto, Set<MultipartFile> documents) throws IOException {
        Message message = new Message();
        message.setText(messagePostDto.getText());
        message.setSubject(messagePostDto.getSubject());

        message.setFromUser(messagePostDto.getFromUser());

        message.setToUsers(messagePostDto.getToUsers());

        Set<File> fileSet = new HashSet<>();
        // if (messagePostDto.getAttachments().size() != 0) {
        //fileSet = message.getFiles();
        message.setFiles(fileSet);
       // }
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

        return messageGetDto;
    }
}
