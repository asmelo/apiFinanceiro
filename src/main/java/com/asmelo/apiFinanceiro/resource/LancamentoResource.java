package com.asmelo.apiFinanceiro.resource;

import com.asmelo.apiFinanceiro.model.Lancamento;
import com.asmelo.apiFinanceiro.model.PalavraChave;
import com.asmelo.apiFinanceiro.model.Subcategoria;
import com.asmelo.apiFinanceiro.repository.LancamentoRepository;
import com.asmelo.apiFinanceiro.repository.PalavraChaveRepository;
import com.asmelo.apiFinanceiro.repository.SubcategoriaRepository;
import com.asmelo.apiFinanceiro.service.ArquivoOfx;
import com.fasterxml.jackson.core.JsonParseException;
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
import java.math.BigDecimal;
import java.util.*;

import org.json.JSONArray;

@RestController
@RequestMapping(value = "lancamentos")
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    SubcategoriaRepository subcategoriaRepository;

    @Autowired
    PalavraChaveRepository palavraChaveRepository;

    @Autowired
    ArquivoOfx arquivoOfx;

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @GetMapping(path = "")
    public HashMap<String, List> findByMonth(@RequestParam("ano") int ano, @RequestParam("mes") int mes) {
        HashMap<String, List> map = new HashMap<>();
        List<Lancamento> lancamentos = lancamentoRepository.findLancamentoByMonth(ano, mes);
        List<Subcategoria> subcategorias = subcategoriaRepository.findAll();

        map.put("subcategorias", subcategorias );
        map.put("lancamentos", lancamentos);

        return map;

    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @GetMapping(path = "{idLancamento}")
    public HashMap<String, Lancamento> findById(@PathVariable Integer idLancamento) {
        HashMap<String, Lancamento> map = new HashMap<>();
        map.put("lancamento", lancamentoRepository.findById(idLancamento).get());
        return map;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @PostMapping("")
    public HashMap<String, Lancamento> save(@RequestBody final String lancamento) {
        try {
            JSONObject jsonObj = new JSONObject(lancamento);
            String lancamentoJson = jsonObj.get("lancamento").toString();
            ObjectMapper mapper = new ObjectMapper();
            Reader reader = new StringReader(lancamentoJson);
            Lancamento lancamentoObj = mapper.readValue(reader, Lancamento.class);

            if(lancamentoObj.getCdtipo() == 2){
                lancamentoObj.setValor(lancamentoObj.getValor().multiply(BigDecimal.valueOf(-1)));
            }

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

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @PutMapping("/{idLancamento}")
    public HashMap<String, Lancamento> update(@PathVariable Integer idLancamento, @RequestBody final String lancamento) {
        try {
            JSONObject jsonObj = new JSONObject(lancamento);
            String lancamentoJson = jsonObj.get("lancamento").toString();
            ObjectMapper mapper = new ObjectMapper();
            Reader reader = new StringReader(lancamentoJson);
            Lancamento lancamentoObj = mapper.readValue(reader, Lancamento.class);
            lancamentoObj.setId(idLancamento);

            if((lancamentoObj.getCdtipo() == 2 && lancamentoObj.getValor().compareTo(BigDecimal.ZERO) > 0) ||
                    lancamentoObj.getCdtipo() == 1 && lancamentoObj.getValor().compareTo(BigDecimal.ZERO) < 0){
                lancamentoObj.setValor(lancamentoObj.getValor().multiply(BigDecimal.valueOf(-1)));
            }

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

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @DeleteMapping("/{idLancamento}")
    public Object delete(@PathVariable Integer idLancamento) {
        lancamentoRepository.deleteById(idLancamento);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://54.233.130.189"})
    @PostMapping("/importarArquivoOfx")
    public Object importarArquivoOfx(@RequestParam Map<String, String> payload) {
        try {
            String idConta = payload.get("idconta");//pBody.get("idConta").toString();

            List<Lancamento> lancamentos = arquivoOfx.recuperaLancamentos("/home/ubuntu/extrato.ofx", idConta);
            //List<Lancamento> lancamentos = arquivoOfx.recuperaLancamentos("C:\\Users\\asmel\\Documentos\\extrato.ofx", idConta);

            lancamentos = defineCategorias(lancamentos);

            for(Lancamento lancamento : lancamentos){
                Calendar cal = Calendar.getInstance();
                cal.setTime(lancamento.getDtlancamento());
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH) + 1;
                Lancamento lancJaExistente = lancamentoRepository.verificaReferenciaOfx(lancamento.getReferenciaofx(), ano, mes);
                if(lancJaExistente == null) {
                    lancamento.setIdconta(Integer.parseInt(idConta));
                    lancamentoRepository.save(lancamento);
                }else{
                    System.out.println("LANCAMENTO JA EXISTENTE: " + lancamento.getDtlancamento() + " - " + lancamento.getDescricao() + " - " + lancamento.getValor());
                }
            }

            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Lancamento> defineCategorias(List<Lancamento> lancamentos) {
        List<PalavraChave> listaPalavraChaves = palavraChaveRepository.findAll();
        for(Lancamento lancamento : lancamentos){
            for(PalavraChave palavraChave : listaPalavraChaves) {
                String descricao = lancamento.getDescricao().toLowerCase();
                String palavra = palavraChave.getPalavra().toLowerCase();
                if(descricao.contains(palavra)){
                    if(lancamento.getSubcategoria() != null){
                        System.out.println("Mais de uma palavra-chave foi indentificada para um lançamento");
                        System.out.println("Descrição do lançamento: " + descricao);
                        System.out.println("Palavra-chave: " + palavra);
                    }else {
                        lancamento.setSubcategoria(palavraChave.getIdsubcategoria());
                    }
                }
            }
        }
        return lancamentos;
    }
}
