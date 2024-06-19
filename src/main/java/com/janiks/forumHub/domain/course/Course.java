package com.janiks.forumHub.domain.course;

import com.janiks.forumHub.dtos.CourseData;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Course")
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Category category;

    public void update(CourseData data) {
        if(isItBlank(data.name())){
            this.name = data.name();
        }
        if(isItBlank(String.valueOf(data.category()))){
            this.category = data.category();
        }
    }

    private boolean isItBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
