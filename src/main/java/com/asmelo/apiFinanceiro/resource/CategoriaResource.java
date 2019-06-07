package com.asmelo.apiFinanceiro.resource;

import com.asmelo.apiFinanceiro.model.Categoria;
import com.asmelo.apiFinanceiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping(value = "/all")
    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    @PostMapping(value = "/load")
    public List<Categoria> persist(@RequestBody final Categoria categoria) {
        categoriaRepository.save(categoria);
        return categoriaRepository.findAll();
    }
}
