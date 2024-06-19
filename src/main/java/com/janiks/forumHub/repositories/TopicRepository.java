package com.janiks.forumHub.repositories;

import com.janiks.forumHub.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query(value = """
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM topicos
        WHERE LEVENSHTEIN(title, :title) <= 5
        """,
            nativeQuery = true)
    boolean existsSimilarTitle(@Param("title") String title);
    @Query(value = """
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM topicos
        WHERE LEVENSHTEIN(message, :message) <= 20
        """,
            nativeQuery = true)
    boolean existsSimilarMessage(@Param("message") String message);
    @Query("SELECT t FROM Topic t JOIN FETCH t.course WHERE t.id = :id")
    Optional<Topic> findByIdWithCourse(Long id);
}