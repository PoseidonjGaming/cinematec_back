package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.RoleDTO;
import fr.poseidonj.cinematec_back.service.IRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleDTO, IRoleService>{
    protected RoleController(IRoleService service) {
        super(service);
    }
}
