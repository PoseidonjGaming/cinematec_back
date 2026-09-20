package fr.poseidonj.cinematec_back.models.entities;

import fr.poseidonj.cinematec_back.utilities.annotation.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Json(display = "username")
public class User extends BaseEntity {
    @Indexed(unique = true)
    private String username;
    private String password;
    private String roleId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
