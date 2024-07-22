package fr.poseidonj.cinematec_back.controllers;

import fr.poseidonj.cinematec_back.models.dtos.BaseDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SearchDTO;
import fr.poseidonj.cinematec_back.models.dtos.special.SortDTO;
import fr.poseidonj.cinematec_back.service.IBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<D extends BaseDTO, S extends IBaseService<D>> {
    private final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<D>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/byIds")
    public ResponseEntity<List<D>> getByIds(@RequestBody List<String> ids){
        return ResponseEntity.ok(service.getByIds(ids));
    }

    @PostMapping("/search")
    public ResponseEntity<List<D>> search(@RequestBody SearchDTO<D> searchDTO){
        return ResponseEntity.ok(service.search(searchDTO));
    }

    @PostMapping("/sort")
    public ResponseEntity<List<D>> sort(@RequestBody SortDTO sortDTO){
        return  ResponseEntity.ok(service.sort(sortDTO));
    }

    @PostMapping("/save")
    public void save(@RequestBody D dto) {
        service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
