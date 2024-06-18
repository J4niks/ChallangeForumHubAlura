package com.janiks.forumHub.repositories;

import com.janiks.forumHub.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
