package com.janiks.forumHub.domain.reply;

import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.dtos.ReplyUpdate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Reply")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Boolean soluction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topic topic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(ReplyUpdate updateData, boolean setSoluction) {
        if(isItBlank(updateData.message())){
           this.message = updateData.message();
        }
        if(setSoluction){
            this.soluction = updateData.soluction();
        }
    }

    private boolean isItBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
