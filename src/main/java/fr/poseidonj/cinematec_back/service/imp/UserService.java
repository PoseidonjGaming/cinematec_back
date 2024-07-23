package fr.poseidonj.cinematec_back.service.imp;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.models.entities.User;
import fr.poseidonj.cinematec_back.repositories.IUserRepository;
import fr.poseidonj.cinematec_back.service.IUserService;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserDTO> implements IUserService {

    protected UserService(IUserRepository repository, IMapper mapper) {
        super(repository, User.class, UserDTO.class, mapper);
    }
}
