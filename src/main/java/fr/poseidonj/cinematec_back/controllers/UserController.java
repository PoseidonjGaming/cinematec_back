package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.security.CredentialDTO;
import fr.poseidonj.cinematec_back.security.JwtResponse;
import fr.poseidonj.cinematec_back.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserDTO, IUserService> {
    protected UserController(IUserService service) {
        super(service);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody CredentialDTO dto) {
        return ResponseEntity.ok(service.authentication(dto));
    }

    @PostMapping("/registration")
    public void registration(@RequestBody CredentialDTO dto) {
        service.registration(dto);
    }

    @PostMapping("/update/password")
    public void updatePassword(@RequestBody CredentialDTO dto) {
        service.updatePassword(dto);
    }
}
