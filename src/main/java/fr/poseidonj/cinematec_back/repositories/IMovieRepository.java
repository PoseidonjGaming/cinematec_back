package fr.poseidonj.cinematec_back.repositories;

import fr.poseidonj.cinematec_back.models.entities.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends IBaseRepository<Movie> {
}
