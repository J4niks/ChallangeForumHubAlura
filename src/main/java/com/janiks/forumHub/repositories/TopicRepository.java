package com.janiks.forumHub.repositories;

import com.janiks.forumHub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
            FROM topicos
            WHERE LEVENSHTEIN(title, :title) <= 5
               OR LEVENSHTEIN(message, :message) <= 20
            """,
            nativeQuery = true)
    boolean existsSimilarTopicsByLevenshteinDistance(@Param("title") String title, @Param("message") String message);

}