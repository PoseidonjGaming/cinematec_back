package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.service.IBaseService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public abstract class BaseController<D extends BaseDTO, S extends IBaseService<D>> {
    private final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<D> getAll() {
        return service.getAll();
    }
}
