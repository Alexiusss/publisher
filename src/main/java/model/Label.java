package model;

import lombok.ToString;

@ToString(callSuper = true)
public class Label extends BaseEntity {
    String name;
    Status status;
}