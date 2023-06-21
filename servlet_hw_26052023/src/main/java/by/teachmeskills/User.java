package by.teachmeskills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
    private String name;
    private String password;

    public User() {
        this.name = "isEmpty";
        this.password = "isEmpty";
    }
}
