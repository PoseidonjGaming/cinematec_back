package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserDTO, IUserService>{
    protected UserController(IUserService service) {
        super(service);
    }
}
