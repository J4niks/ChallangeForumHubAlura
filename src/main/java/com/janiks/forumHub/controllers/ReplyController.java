    package com.janiks.forumHub.controllers;

    import com.janiks.forumHub.dtos.ReplyCreationData;
    import com.janiks.forumHub.dtos.ReplyUpdate;
    import com.janiks.forumHub.repositories.ReplyRepository;
    import com.janiks.forumHub.services.ReplyService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.security.SecurityRequirement;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("{topic_id}/resposta")
    @SecurityRequirement(name = "bearer-key")
    @Tag(name = "Respostas a tópicos")
    public class ReplyController {

        @Autowired
        private ReplyRepository repository;
        @Autowired
        private ReplyService replyService;

        @PostMapping
        @Transactional
        @Operation(summary = "Responder", description = "Enviar uma nova resposta a um tópico")
        public ResponseEntity newReply(@PathVariable Long topic_id, @RequestBody @Valid ReplyCreationData data, HttpServletRequest request){
            var token = request.getHeader("Authorization").replace("Bearer ", "");
            var dto = this.replyService.create(topic_id,data, token);
            return ResponseEntity.ok().body(dto);
        }

        @PutMapping
        @Transactional
        @Operation(summary = "Responder", description = "Editar resposta enviada. Criadores do tópico e/ou ADMIN podem marcar resposta como 'Solução', mudando o status do tópico para 'RESOLVIDO'")
        public ResponseEntity updateReply(@RequestBody @Valid ReplyUpdate data, @PathVariable Long topic_id, HttpServletRequest request){
            var token = request.getHeader("Authorization").replace("Bearer ", "");
            var dto = this.replyService.update(data, topic_id, token);
            return ResponseEntity.ok(dto);
        }

        @DeleteMapping("/{reply_id}")
        @Transactional
        @Operation(summary = "Apagar", description = "Criadores da resposta e/ou ADMIN podem apagá-las")
        public ResponseEntity deleteReply(@PathVariable Long reply_id , HttpServletRequest request){
            var token = request.getHeader("Authorization").replace("Bearer ", "");
            if(replyService.delete(reply_id, token)){
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.badRequest().build();
        }

    }
