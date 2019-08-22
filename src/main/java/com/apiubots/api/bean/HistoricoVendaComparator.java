package com.apiubots.api.bean;

import com.apiubots.api.dto.HistoricoVendaDTO;

import java.util.Comparator;

public class HistoricoVendaComparator implements Comparator<HistoricoVendaDTO> {

    public int compare(HistoricoVendaDTO ha, HistoricoVendaDTO hb) {
        return Double.compare(hb.getPrecoTotal().doubleValue(), ha.getPrecoTotal().doubleValue());
    }
}

