package my.flick.rd.springproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.flick.rd.springproject.model.enums.Role;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;

    String email;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;
}
