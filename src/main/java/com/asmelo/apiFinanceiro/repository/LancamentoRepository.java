package com.asmelo.apiFinanceiro.repository;

import com.asmelo.apiFinanceiro.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

    @Query("SELECT l FROM Lancamento l WHERE YEAR(l.dtlancamento) = ?1 AND MONTH(l.dtlancamento) = ?2")
    List<Lancamento> findLancamentoByMonth(Integer ano, Integer mes);

    @Query("SELECT l FROM Lancamento l WHERE referenciaofx = ?1 AND YEAR(l.dtlancamento) = ?2 AND MONTH(l.dtlancamento) = ?3 ORDER BY dtlancamento")
    Lancamento verificaReferenciaOfx(String referenciaOfx, Integer ano, Integer mes);

}