package model;

import lombok.*;

import java.util.List;

@ToString(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
public class Writer extends BaseEntity{
    String firstName;
    String lastName;
    List<Post> posts;
    Status status;
}