package com.janiks.forumHub.domain.topic;

import com.janiks.forumHub.domain.course.Course;
import com.janiks.forumHub.domain.reply.Reply;
import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.dtos.TopicUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Topic(String title, String message, LocalDateTime now, Status status, Course course, User user) {
        this.title = title;
        this.message = message;
        this.creationDate = now;
        this.status = status;
        this.course = course;
        this.user = user;
    }

}
