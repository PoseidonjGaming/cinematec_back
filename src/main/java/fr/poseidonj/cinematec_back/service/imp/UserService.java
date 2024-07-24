package fr.poseidonj.cinematec_back.service.imp;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.models.entities.User;
import fr.poseidonj.cinematec_back.repositories.IUserRepository;
import fr.poseidonj.cinematec_back.service.IUserService;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User, UserDTO, IUserRepository> implements IUserService, UserDetailsService {

    protected UserService(IUserRepository repository, IMapper mapper) {
        super(repository, User.class, UserDTO.class, mapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = repository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole().name())));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
