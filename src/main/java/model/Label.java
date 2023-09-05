package model;

import lombok.*;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Label extends BaseEntity {
    String name;
    Status status;
}