package com.asmelo.apiFinanceiro.service;

import com.asmelo.apiFinanceiro.model.Lancamento;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArquivoOfx {

    public List<Lancamento> recuperaLancamentos(String filePath, String idConta) throws Exception{
        ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>();

        Path arquivoOfx = Paths.get(filePath);
        Charset charset = Charset.forName("ISO-8859-1"); //=Latin1
        try (BufferedReader reader = Files.newBufferedReader(arquivoOfx, charset)) {
            String linha = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            while ((linha = reader.readLine()) != null) {
                if(getTagNome(linha).equals("STMTTRN")){ //Início do lançamento
                    Lancamento lancamento = new Lancamento();

                    reader.readLine();// Ignora tag TRNTYPE

                    String data = getTagValor(reader.readLine());
                    lancamento.setDtlancamento(simpleDateFormat.parse(data.substring(0,8)));

                    String valor = getTagValor(reader.readLine());
                    lancamento.setValor(new BigDecimal(valor));

                    if(lancamento.getValor().compareTo(BigDecimal.ZERO) > 0) {
                        lancamento.setCdtipo(1);
                    }else{
                        lancamento.setCdtipo(2);
                    }

                    lancamento.setReferenciaofx(getTagValor(reader.readLine()));

                    reader.readLine();// Ignora tag CHECKNUM

                    if(!idConta.equals("2")) {
                        reader.readLine();// Ignora tag REFNUM
                    }

                    lancamento.setDescricao(getTagValor(reader.readLine()));

                    reader.readLine();// </STMTTRN>

                    lancamentos.add(lancamento);
                }
            }
        } catch (IOException x) {
            throw (x);
        }

        return lancamentos;
    }

    private String getTagNome(String linha){
        String tagName = "";
        int inicio = linha.indexOf('<') + 1;
        int fim = linha.indexOf('>');

        if(inicio != -1 && fim != -1){
            tagName = linha.substring(inicio, fim);
        }

        return tagName;
    }

    private String getTagValor(String linha){
        String tagValor = "";
        int inicio = linha.indexOf('>') + 1;
        int fim = linha.indexOf("</");

        if(inicio != -1){
            tagValor = linha.substring(inicio, fim);
        }

        return tagValor;
    }
}
