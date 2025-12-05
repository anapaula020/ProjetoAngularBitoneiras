package br.unitins.tp1.service;

import br.unitins.tp1.dto.PixDTO;
import br.unitins.tp1.dto.mercadopago.MercadoPagoPixResponseDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.service.PixServiceImpl.CardPaymentRequest;

public interface PixService {
    public static PixDTO generatePix(Double amount, Cliente cliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generatePix'");
    }

    public PixDTO createCardPayment(CardPaymentRequest cardRequest, Cliente cliente);

    public MercadoPagoPixResponseDTO processPixPayment(Long pedidoId, PixDTO pixDto);
}