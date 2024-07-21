package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.MovieDTO;
import fr.poseidonj.cinematec_back.models.entities.Movie;
import fr.poseidonj.cinematec_back.repositories.IMovieRepository;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends BaseService<Movie, MovieDTO> implements IMovieService {

    protected MovieService(IMovieRepository repository, IMapper mapper) {
        super(repository, Movie.class, MovieDTO.class, mapper);
    }
}
