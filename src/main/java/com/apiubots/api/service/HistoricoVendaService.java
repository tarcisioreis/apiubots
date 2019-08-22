package com.apiubots.api.service;

import com.apiubots.api.bean.HistoricoVendaComparator;
import com.apiubots.api.constantes.Constantes;
import com.apiubots.api.dto.HistoricoVendaDTO;
import com.apiubots.api.dto.ProdutoDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HistoricoVendaService {

    private final ClienteService clienteService;

    private List<HistoricoVendaDTO> lista;
    private HistoricoVendaDTO historicoVendaDTO;

    private OkHttpClient httpClient;
    private Response response;
    private Request request;

    @Autowired
    public HistoricoVendaService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public List<HistoricoVendaDTO> findAll() {
        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<HistoricoVendaDTO>();

        request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.VERSION + Constantes.ENDPOINT_HISTORICO_VENDA)
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            String retorno = response.body().string();

            JSONArray jsonArray = new JSONArray(retorno);
            JSONArray jsonArrayItens = null;

            JSONObject jsonObject = null;
            JSONObject jsonObjectItem = null;

            for (int i = 0; jsonArray.length() > i; i++) {
                jsonObject = jsonArray.getJSONObject(i);

                historicoVendaDTO = new HistoricoVendaDTO();

                historicoVendaDTO.setCodigo(jsonObject.getString("codigo"));
                historicoVendaDTO.setData(jsonObject.getString("data").replace("-", "/"));
                historicoVendaDTO.setPrecoTotal(jsonObject.getDouble("valorTotal"));

                historicoVendaDTO.setClienteDTO(clienteService.findByCpf(jsonObject.getString("cliente")));

                List<ProdutoDTO> listaProdutoDTO = new ArrayList<ProdutoDTO>();

                jsonArrayItens = jsonObject.getJSONArray("itens");
                for (int j = 0; jsonArrayItens.length() > j; j++) {
                    jsonObjectItem = jsonArrayItens.getJSONObject(j);

                    ProdutoDTO produtoDTO = new ProdutoDTO();

                    if (jsonObjectItem.has("codigo")) {
                        produtoDTO.setCodigo(jsonObjectItem.getString("codigo"));
                    }

                    produtoDTO.setDescricao(jsonObjectItem.getString("produto"));
                    produtoDTO.setVariedade(jsonObjectItem.getString("variedade"));
                    produtoDTO.setPaisOrigem(jsonObjectItem.getString("pais"));
                    produtoDTO.setCategoria(jsonObjectItem.getString("categoria"));
                    produtoDTO.setSafra(jsonObjectItem.getString("safra"));
                    produtoDTO.setPreco(jsonObjectItem.getDouble("preco"));

                    listaProdutoDTO.add(produtoDTO);
                }

                historicoVendaDTO.setItens(listaProdutoDTO);

                lista.add(historicoVendaDTO);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return lista;
    }

    public List<HistoricoVendaDTO> findClienteMaiorValorTotalCompra() {
        List<HistoricoVendaDTO> retorno = new ArrayList<HistoricoVendaDTO>();
        historicoVendaDTO = null;
        Double valor = 0.00;

        int i = 0;
        boolean achou = false;

        try {
            lista = this.findAll();

            do {

                if (i == 0) {   // Pega o primeiro elemento
                    historicoVendaDTO = new HistoricoVendaDTO();

                    historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                    valor = lista.get(i).getPrecoTotal().doubleValue();

                    for (int j = (i + 1); j < lista.size(); j++) {
                        if (lista.get(j).getClienteDTO().getId() == historicoVendaDTO.getClienteDTO().getId()) {
                            valor += lista.get(j).getPrecoTotal().doubleValue();
                        }
                    }

                    historicoVendaDTO.setPrecoTotal(valor);
                    historicoVendaDTO.setItens(null);
                    historicoVendaDTO.setCodigo(null);
                    historicoVendaDTO.setData(null);

                    retorno.add(historicoVendaDTO);
                    valor = 0.00;
                } else if (lista.get(i).getClienteDTO().getId() != historicoVendaDTO.getClienteDTO().getId()) {

                    achou = false;
                    for (int ii = 0; ii < retorno.size(); ii++) {
                        if (retorno.get(ii).getClienteDTO().getId() == lista.get(i).getClienteDTO().getId()) {
                            achou = true;
                            break;
                        }
                    }

                    if (!achou) {
                        historicoVendaDTO = null;
                        historicoVendaDTO = new HistoricoVendaDTO();

                        historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                        valor = lista.get(i).getPrecoTotal().doubleValue();

                        for (int j = (i + 1); j < lista.size(); j++) {
                            if (lista.get(j).getClienteDTO().getId() == historicoVendaDTO.getClienteDTO().getId()) {
                                valor += lista.get(j).getPrecoTotal().doubleValue();
                            }
                        }

                        historicoVendaDTO.setPrecoTotal(valor);
                        historicoVendaDTO.setItens(null);
                        historicoVendaDTO.setCodigo(null);
                        historicoVendaDTO.setData(null);

                        retorno.add(historicoVendaDTO);
                        valor = 0.00;
                    }
                }

                i++;
            } while(i < lista.size());
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }

        Collections.sort(retorno, new HistoricoVendaComparator());

        return retorno;
    }

    public List<HistoricoVendaDTO> findClienteMaiorValorTotalCompraUnicaPorAno(int ano) {
        List<HistoricoVendaDTO> listaUnica = new ArrayList<HistoricoVendaDTO>();
        historicoVendaDTO = new HistoricoVendaDTO();

        int i = 0;
        boolean achou = false;

        String data = null;
        String dataOcorrencia = null;

        int contador = 0;

        try {
            lista = this.findAll();

            do {

                data = lista.get(i).getData();
                data = data.substring(data.length() - 4);

                if (Integer.parseInt(data) == ano) {

                    if (listaUnica.size() == 0) {   // Primeiro a ser testado a ocorrencia de uma compra no ano informado
                        historicoVendaDTO = new HistoricoVendaDTO();

                        historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                        historicoVendaDTO.setPrecoTotal(lista.get(i).getPrecoTotal());

                        contador++;
                        for (int j = 0; j < lista.size(); j++) {
                            dataOcorrencia = lista.get(j).getData();
                            dataOcorrencia = dataOcorrencia.substring(dataOcorrencia.length() - 4);

                            if (j == i) { continue; }

                            if (lista.get(j).getClienteDTO().getId() == historicoVendaDTO.getClienteDTO().getId() &&
                                Integer.parseInt(dataOcorrencia)     == ano) {
                                contador++;

                                if (contador > 1) { break; }
                            }
                        }

                        if (contador == 1) {
                            historicoVendaDTO.setItens(null);
                            historicoVendaDTO.setCodigo(null);
                            historicoVendaDTO.setData(null);

                            listaUnica.add(historicoVendaDTO);
                        }

                        contador = 0;
                    } else if (lista.get(i).getClienteDTO().getId() != historicoVendaDTO.getClienteDTO().getId()) {

                        achou = false;
                        for (int ii = 0; ii < listaUnica.size(); ii++) {
                            if (listaUnica.get(ii).getClienteDTO().getId() == lista.get(i).getClienteDTO().getId()) {
                                achou = true;
                                break;
                            }
                        }

                        if (!achou) {
                            historicoVendaDTO = null;
                            historicoVendaDTO = new HistoricoVendaDTO();

                            historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                            historicoVendaDTO.setPrecoTotal(lista.get(i).getPrecoTotal());

                            contador++;
                            for (int j = 0; j < lista.size(); j++) {
                                dataOcorrencia = lista.get(j).getData();
                                dataOcorrencia = dataOcorrencia.substring(dataOcorrencia.length() - 4);

                                if (j == i) { continue; }

                                if (lista.get(j).getClienteDTO().getId() == historicoVendaDTO.getClienteDTO().getId() &&
                                    Integer.parseInt(dataOcorrencia)     == ano) {
                                    contador++;

                                    if (contador > 1) { break; }
                                }
                            }

                            if (contador == 1) {
                                historicoVendaDTO.setItens(null);
                                historicoVendaDTO.setCodigo(null);
                                historicoVendaDTO.setData(null);

                                listaUnica.add(historicoVendaDTO);
                            }

                            contador = 0;
                        }
                    }
                }

                i++;
            } while(i < lista.size());
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }

        Collections.sort(listaUnica, new HistoricoVendaComparator());

        return listaUnica;
    }

    public List<HistoricoVendaDTO> findClienteFidelidade() {
        List<HistoricoVendaDTO> listaFidelidade = new ArrayList<HistoricoVendaDTO>();
        historicoVendaDTO = new HistoricoVendaDTO();

        int i = 0;
        boolean achou = false;

        try {
            lista = this.findAll();

            do {

                if (listaFidelidade.size() == 0) {   // Primeiro a ser testado a existencia de alguma compra
                    historicoVendaDTO = new HistoricoVendaDTO();

                    historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                    historicoVendaDTO.setPrecoTotal(lista.get(i).getPrecoTotal());
                    historicoVendaDTO.setItens(lista.get(i).getItens());

                    historicoVendaDTO.setCodigo(null);
                    historicoVendaDTO.setData(null);

                    listaFidelidade.add(historicoVendaDTO);
                } else if (lista.get(i).getClienteDTO().getId() != historicoVendaDTO.getClienteDTO().getId()) {

                    achou = false;
                    for (int ii = 0; ii < listaFidelidade.size(); ii++) {
                        if (listaFidelidade.get(ii).getClienteDTO().getId() == lista.get(i).getClienteDTO().getId()) {
                            achou = true;
                            break;
                        }
                    }

                    if (!achou) {
                        historicoVendaDTO = null;
                        historicoVendaDTO = new HistoricoVendaDTO();

                        historicoVendaDTO.setClienteDTO(lista.get(i).getClienteDTO());
                        historicoVendaDTO.setPrecoTotal(lista.get(i).getPrecoTotal());
                        historicoVendaDTO.setItens(lista.get(i).getItens());

                        historicoVendaDTO.setCodigo(null);
                        historicoVendaDTO.setData(null);

                        listaFidelidade.add(historicoVendaDTO);
                    }
                }

                i++;
            } while(i < lista.size());
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }

        Collections.sort(listaFidelidade, new HistoricoVendaComparator());

        return listaFidelidade;
    }

}
