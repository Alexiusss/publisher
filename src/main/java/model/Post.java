package model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {

    @Expose
    public String content;

    @Expose
    public LocalDate created;

    @Expose(serialize = false)
    public List<Label> labels;

    @Expose
    public PostStatus postStatus;

    @Expose
    public String writerId;
}