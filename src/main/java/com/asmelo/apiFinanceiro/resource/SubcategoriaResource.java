package com.asmelo.apiFinanceiro.resource;

import com.asmelo.apiFinanceiro.model.Subcategoria;
import com.asmelo.apiFinanceiro.repository.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/subcategorias")
public class SubcategoriaResource {

    @Autowired
    SubcategoriaRepository subcategoriaRepository;

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @GetMapping(value = "")
    public HashMap<String, List<Subcategoria>> findAll() {
        HashMap<String, List<Subcategoria>> map = new HashMap<>();
        map.put("subcategorias", subcategoriaRepository.findAll());
        return map;
    }
}
