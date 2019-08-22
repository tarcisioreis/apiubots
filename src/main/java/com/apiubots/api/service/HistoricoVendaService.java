package com.apiubots.api.service;

import com.apiubots.api.constantes.Constantes;
import com.apiubots.api.dto.ClienteDTO;
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

    private OkHttpClient httpClient;
    private Response response;
    private List<HistoricoVendaDTO> lista;
    private HistoricoVendaDTO historicoVendaDTO;
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

}
