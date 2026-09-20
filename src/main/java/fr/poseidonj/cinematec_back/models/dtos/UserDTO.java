package fr.poseidonj.cinematec_back.models.dtos;

import fr.poseidonj.cinematec_back.utilities.annotation.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    private String username;
    @Json(type = "role", display = "name")
    private String roleId;
    private String firstname;
    private String lastname;
    @Json(type = "mail")
    private String email;
    @Json(type = "phone")
    private String phoneNumber;
}
