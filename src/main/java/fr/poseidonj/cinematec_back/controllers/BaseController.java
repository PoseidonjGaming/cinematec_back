package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.service.IBaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/save")
    public void save(@RequestBody D dto) {
        service.save(dto);
    }
}
