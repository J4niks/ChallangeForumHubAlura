package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.TopicCreationData;
import com.janiks.forumHub.dtos.TopicData;
import com.janiks.forumHub.dtos.TopicDataWithReplies;
import com.janiks.forumHub.dtos.TopicUpdate;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.repositories.TopicRepository;
import com.janiks.forumHub.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SecurityValidation securityValidation;

    @PostMapping
    @Transactional
    @Operation(summary = "Criar tópico", description = "Criação de um novo tópico")
    public ResponseEntity createNewTopic(@RequestBody @Valid TopicCreationData data, HttpServletRequest request){
        var token = request.getHeader("Authorization").replace("Bearer ", "");
        var dto = topicService.create(data, token);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar tópicos (10 por pagina ordenados por data de criação)")
    public ResponseEntity<Page<TopicData>> getAllTopics(@PageableDefault(size= 10, sort = {"creationDate","course"}) Pageable pageable){
        var topics = this.topicRepository.findAll(pageable).map(TopicData::new);
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista tópico por id", description = "Lista um tópico específico e todas as suas respostas")
    public ResponseEntity getTopic(@PathVariable Long id){
        Optional<Topic> topic = this.topicRepository.findByIdWithCourse(id);
        if(topic.isPresent()){
            return ResponseEntity.ok().body(new TopicDataWithReplies(topic.get()));
        }
        return ResponseEntity.badRequest().body("Tópico não existe");
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Editar", description = "Edição de tópico (Apenas criador e/ou ADMIN)")
    public ResponseEntity<TopicData> editTopic(@RequestBody @Valid TopicUpdate data, @PathVariable Long id, HttpServletRequest request){
        var token = request.getHeader("Authorization").replace("Bearer ", "");
        var dto = topicService.update(id, token, data);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Apagar", description = "Apagar tópico (Apenas criador e/ou ADMIN)")
    public ResponseEntity deleteTopic(@PathVariable Long id, HttpServletRequest request){
        var token = request.getHeader("Authorization").replace("Bearer ", "");
        if(this.securityValidation.haveAuthoritiesForTopic(id,token)){
            this.topicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
