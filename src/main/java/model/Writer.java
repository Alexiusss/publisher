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
    String firstName;
    @Expose
    String lastName;
    @Expose(serialize = false)
    List<Post> posts;
    @Expose
    Status status;
}