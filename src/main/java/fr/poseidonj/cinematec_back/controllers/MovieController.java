package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.MovieDTO;
import fr.poseidonj.cinematec_back.service.IMovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController extends BaseController<MovieDTO, IMovieService> {
    protected MovieController(IMovieService service) {
        super(service);
    }
}
