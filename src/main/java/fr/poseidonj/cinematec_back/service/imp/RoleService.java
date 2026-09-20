package fr.poseidonj.cinematec_back.service.imp;

import fr.poseidonj.cinematec_back.models.dtos.RoleDTO;
import fr.poseidonj.cinematec_back.models.entities.Role;
import fr.poseidonj.cinematec_back.repositories.IRoleRepository;
import fr.poseidonj.cinematec_back.service.IRoleService;
import fr.poseidonj.cinematec_back.utilities.mapper.IMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleDTO, IRoleRepository> implements IRoleService {
    protected RoleService(IRoleRepository repository, IMapper mapper) {
        super(repository, Role.class, RoleDTO.class, mapper);
    }
}
