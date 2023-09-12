package model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.List;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Writer extends BaseEntity {
    @Expose
    public String firstName;
    @Expose
    public String lastName;
    @Expose(serialize = false)
    public List<Post> posts;
    @Expose
    public Status status;
}