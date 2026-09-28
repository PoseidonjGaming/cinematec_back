package fr.poseidonj.cinematec_back.repositories;

import fr.poseidonj.cinematec_back.models.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends IBaseRepository<Role>{
    Optional<Role> findByName(String name);
}
