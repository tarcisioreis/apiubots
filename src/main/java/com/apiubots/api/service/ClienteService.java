package com.apiubots.api.service;

import com.apiubots.api.constantes.Constantes;
import com.apiubots.api.dto.ClienteDTO;
import com.apiubots.api.dto.HistoricoVendaDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClienteService {

    private List<ClienteDTO> lista;
    private ClienteDTO clienteDTO;

    private OkHttpClient httpClient;
    private Response response;
    private Request request;

    @Autowired
    public ClienteService() {
        super();
    }

    public List<ClienteDTO> findAll() {
        httpClient = new OkHttpClient();
        response = null;
        lista = new ArrayList<ClienteDTO>();

        request = new Request.Builder()
                .url(Constantes.URL_BASE + Constantes.VERSION + Constantes.ENDPOINT_CADASTRO_CLIENTE)
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            String retorno = response.body().string();

            JSONArray jsonArray = new JSONArray(retorno);

            JSONObject jsonObject = null;
            for (int i = 0; jsonArray.length() > i; i++) {
                jsonObject = jsonArray.getJSONObject(i);

                clienteDTO = new ClienteDTO();

                clienteDTO.setId(jsonObject.getInt("id"));
                clienteDTO.setNome(jsonObject.getString("nome"));
                clienteDTO.setCpf(jsonObject.getString("cpf"));

                lista.add(clienteDTO);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return lista;
    }

    public ClienteDTO findByCpf(String cpf) {

        clienteDTO = new ClienteDTO();

        try {
            lista = this.findAll();

            for(ClienteDTO dto : lista) {
                String objCpf = dto.getCpf();
                String paramCpf = cpf.substring(cpf.length() - 2);

                objCpf = objCpf.substring(objCpf.length() - 2);

                if (paramCpf.equals(objCpf)) {
                    return dto;
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }

    }

}
