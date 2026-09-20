package fr.poseidonj.cinematec_back.service.imp;

import fr.poseidonj.cinematec_back.models.dtos.UserDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.entities.Role;
import fr.poseidonj.cinematec_back.models.entities.User;
import fr.poseidonj.cinematec_back.repositories.IRoleRepository;
import fr.poseidonj.cinematec_back.repositories.IUserRepository;
import fr.poseidonj.cinematec_back.security.CredentialDTO;
import fr.poseidonj.cinematec_back.security.JwtResponse;
import fr.poseidonj.cinematec_back.service.IUserService;
import fr.poseidonj.cinematec_back.utilities.JwtUtil;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User, UserDTO, IUserRepository> implements IUserService, UserDetailsService {
    protected final JwtUtil jwtTokenUtil;
    protected final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final IRoleRepository roleRepository;
    @Value("${user.default}")
    private String defaultPassword;

    @Lazy
    protected UserService(IUserRepository repository, IMapper mapper, JwtUtil jwtTokenUtil, AuthenticationManager authenticationManager, PasswordEncoder encoder, IRoleRepository roleRepository) {
        super(repository, User.class, UserDTO.class, mapper);
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(UserDTO dto) {
        User user = mapper.convert(dto, entityClass);
        user.setPassword(encoder.encode(defaultPassword));

        repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = repository.findByUsername(username);
            Role role = roleRepository.findById(user.getRoleId()).orElse(new Role());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    List.of(new SimpleGrantedAuthority(role.getId())));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void updatePassword(CredentialDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        repository.save(user);
    }

    @Override
    public JwtResponse authentication(CredentialDTO dto) {
        List<UserDTO> results = search(new SearchDTO<>(mapper.convert(dto, UserDTO.class), false,
                ExampleMatcher.MatchMode.ALL, ExampleMatcher.StringMatcher.EXACT,
                null, null));
        if (!results.isEmpty()) {
            authenticateManager(dto.getUsername(), dto.getPassword());
            UserDTO user = results.get(0);
            return new JwtResponse(jwtTokenUtil.generateToken(user.getUsername(), roleRepository.findById(user.getRoleId()).orElse(new Role()).getName()));

        }
        return null;
    }

    @Override
    public void registration(CredentialDTO userDTO) {
        User user = mapper.convert(userDTO, entityClass);
        user.setPassword(encoder.encode(userDTO.getPassword()));

        repository.save(user);
    }

    private void authenticateManager(@NotNull String username, @NotNull String password) throws AuthenticationCredentialsNotFoundException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("User doesn't exist");
        }
    }
}
