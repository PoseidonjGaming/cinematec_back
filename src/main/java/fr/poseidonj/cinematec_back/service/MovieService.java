package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.MovieDTO;
import fr.poseidonj.cinematec_back.models.entities.Movie;
import fr.poseidonj.cinematec_back.repositories.IBaseRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends BaseService<Movie, MovieDTO> implements IMovieService {
    public MovieService(IBaseRepository<Movie> repository) {
        super(repository);
    }
}
