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
@Json(display = "name")
public class Role extends BaseEntity{
    @Indexed(unique = true)
    private String name;
}
