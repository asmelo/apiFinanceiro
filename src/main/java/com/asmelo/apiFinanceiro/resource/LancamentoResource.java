package com.asmelo.apiFinanceiro.resource;

import com.asmelo.apiFinanceiro.model.Lancamento;
import com.asmelo.apiFinanceiro.model.Subcategoria;
import com.asmelo.apiFinanceiro.repository.LancamentoRepository;
import com.asmelo.apiFinanceiro.repository.SubcategoriaRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;

@RestController
@RequestMapping(value = "lancamentos")
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    SubcategoriaRepository subcategoriaRepository;

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @GetMapping(path = "")
    public HashMap<String, List> findByMonth(@RequestParam("ano") int ano, @RequestParam("mes") int mes) {
        HashMap<String, List> map = new HashMap<>();
        List<Lancamento> lancamentos = lancamentoRepository.findLancamentoByMonth(ano, mes);
        List<Subcategoria> subcategorias = subcategoriaRepository.findAll();

        map.put("subcategorias", subcategorias );
        map.put("lancamentos", lancamentos);

        return map;

    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @GetMapping(path = "{idLancamento}")
    public HashMap<String, Lancamento> findById(@PathVariable Integer idLancamento) {
        HashMap<String, Lancamento> map = new HashMap<>();
        map.put("lancamento", lancamentoRepository.findById(idLancamento).get());
        return map;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @PostMapping("")
    public HashMap<String, Lancamento> save(@RequestBody final String lancamento) {
        try {
            JSONObject jsonObj = new JSONObject(lancamento);
            String lancamentoJson = jsonObj.get("lancamento").toString();
            ObjectMapper mapper = new ObjectMapper();
            Reader reader = new StringReader(lancamentoJson);
            Lancamento lancamentoObj = mapper.readValue(reader, Lancamento.class);
            Lancamento newLancamento = lancamentoRepository.save(lancamentoObj);
            HashMap<String, Lancamento> map = new HashMap<>();
            map.put("lancamento", newLancamento);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @PutMapping("/{idLancamento}")
    public HashMap<String, Lancamento> update(@PathVariable Integer idLancamento, @RequestBody final String lancamento) {
        try {
            JSONObject jsonObj = new JSONObject(lancamento);
            String lancamentoJson = jsonObj.get("lancamento").toString();
            ObjectMapper mapper = new ObjectMapper();
            Reader reader = new StringReader(lancamentoJson);
            Lancamento lancamentoObj = mapper.readValue(reader, Lancamento.class);
            lancamentoObj.setId(idLancamento);
            lancamentoRepository.save(lancamentoObj);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Lancamento> map = new HashMap<>();
        map.put("lancamento", lancamentoRepository.findById(idLancamento).get());
        return map;

    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.207.40.169"})
    @DeleteMapping("/{idLancamento}")
    public Object delete(@PathVariable Integer idLancamento) {
        lancamentoRepository.deleteById(idLancamento);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
