package com.janiks.forumHub.domain.topic;

import com.janiks.forumHub.domain.course.Course;
import com.janiks.forumHub.dtos.TopicUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    public void update(TopicUpdate data, Course course) {
        if(data.title() !=null){this.title = data.title();}
        if(data.message() !=null){this.message = data.message();}
        if(course != null){this.course = course;}
        if(data.status() !=null){this.status = data.status();}
    }
}
