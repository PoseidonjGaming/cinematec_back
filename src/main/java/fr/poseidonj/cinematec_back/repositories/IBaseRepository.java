package fr.poseidonj.cinematec_back.repositories;

import fr.poseidonj.cinematec_back.models.entities.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IBaseRepository<E extends BaseEntity> extends MongoRepository<E, String> {
    List<E> findByIdIn(List<String> ids);
}
