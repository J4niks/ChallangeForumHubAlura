package com.janiks.forumHub.controllers;

import com.janiks.forumHub.dtos.ReplyCreationData;
import com.janiks.forumHub.dtos.ReplyUpdate;
import com.janiks.forumHub.repositories.ReplyRepository;
import com.janiks.forumHub.services.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{topic_id}/resposta")
public class ReplyController {

    @Autowired
    private ReplyRepository repository;
    @Autowired
    private ReplyService replyService;

    @PostMapping
    @Transactional
    public ResponseEntity newReply(@PathVariable Long topic_id, @RequestBody @Valid ReplyCreationData data){
        var dto = this.replyService.create(topic_id,data);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateReply(@RequestBody @Valid ReplyUpdate data){
        var dto = this.replyService.update(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{reply_id}")
    @Transactional
    public ResponseEntity deleteReply(@PathVariable Long reply_id){
        this.repository.deleteById(reply_id);
        return ResponseEntity.noContent().build();
    }

}
