package model;

import lombok.ToString;

import java.util.List;

@ToString(callSuper = true)
public class Writer extends BaseEntity{
    String firstName;
    String lastName;
    List<Post> posts;
    Status status;
}