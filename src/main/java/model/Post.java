package model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {
    String content;
    LocalDateTime created;
    List<Label> labels;
    PostStatus postStatus;
    String writerId;
}