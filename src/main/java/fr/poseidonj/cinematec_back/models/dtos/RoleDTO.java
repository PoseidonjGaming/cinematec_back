package fr.poseidonj.cinematec_back.models.dtos;

import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseDTO {
    private String name;
}
