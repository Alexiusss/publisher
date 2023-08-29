package model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString(callSuper = true)
public class Post extends BaseEntity {
    String content;
    LocalDateTime created;
    List<Label> posts;
    PostStatus postStatus;
}