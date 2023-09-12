package model;

import com.google.gson.annotations.Expose;
import lombok.*;

@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Label extends BaseEntity {

    @Expose
    public String name;

    @Expose
    public Status status;
}