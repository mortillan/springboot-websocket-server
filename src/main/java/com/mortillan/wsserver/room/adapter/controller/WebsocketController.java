package com.mortillan.wsserver.room.adapter.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.mortillan.wsserver.room.entity.Message;

@RestController
public class WebsocketController {

  @Autowired
  private SimpMessagingTemplate template;

  @MessageMapping("/{topicId}")
  public void broadcastMessage(@DestinationVariable String topicId, @Payload Message message) {
    System.out.printf("TopicID %s %s\n", topicId, message);
    this.template.convertAndSend(String.format("/topic/%s", topicId), message);
  }

  @PostMapping("/topic/{topicId}")
  public Message postMessage(@PathVariable String topicId, @RequestBody Message message) {
    System.out.printf("TopicID %s %s\n", topicId, message);
    this.template.convertAndSend(String.format("/topic/%s", topicId), message);
    return message;
  }
}
