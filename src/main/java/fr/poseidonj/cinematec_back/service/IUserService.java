package fr.poseidonj.cinematec_back.service;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.security.CredentialDTO;
import fr.poseidonj.cinematec_back.security.JwtResponse;

public interface IUserService extends IBaseService<UserDTO> {
    void updatePassword(CredentialDTO dto);
    JwtResponse authentication(CredentialDTO dto);

    void registration(CredentialDTO userDTO);
}
