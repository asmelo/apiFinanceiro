package com.asmelo.apiFinanceiro.resource;

import com.asmelo.apiFinanceiro.model.Categoria;
import com.asmelo.apiFinanceiro.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaRepository categoriaRepository;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "")
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/")
    public HashMap<String, List<Categoria>> save(@RequestBody final Categoria categoria) {
        categoriaRepository.save(categoria);
        HashMap<String, List<Categoria>> map = new HashMap<>();
        map.put("categorias", categoriaRepository.findAll());
        return map;
    }
}
