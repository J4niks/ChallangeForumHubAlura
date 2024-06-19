package com.janiks.forumHub.repositories;

import com.janiks.forumHub.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT r FROM Reply r WHERE r.topic.id = :topicId")
    List<Reply> findAllFromTopic(@Param("topicId")Long id);
}
