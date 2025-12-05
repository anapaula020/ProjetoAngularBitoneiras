package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PixDTO;
import br.unitins.tp1.dto.mercadopago.MercadoPagoPixResponseDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.service.BetoneiraServiceImpl.CardPaymentRequest;
import jakarta.validation.Valid;

public interface PixService {
    public PixDTO generatePix(Double amount, Cliente cliente);
    public PixDTO createCardPayment(CardPaymentRequest cardRequest, Cliente cliente);
    public MercadoPagoPixResponseDTO processPixPayment(Long pedidoId, PixDTO pixDto);
}