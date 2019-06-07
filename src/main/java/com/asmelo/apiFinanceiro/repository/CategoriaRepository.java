package com.asmelo.apiFinanceiro.repository;

import com.asmelo.apiFinanceiro.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
