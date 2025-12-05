package mssaat.org.service;

import java.util.List;

import jakarta.validation.Valid;
import mssaat.org.DTO.AdministradorDTO;
import mssaat.org.DTO.AdministradorResponseDTO;

public interface PixService {
    public PixDTO generatePix(Double amount, Cliente cliente);
    public PixDTO createCardPayment(CardPaymentRequest cardRequest, Cliente cliente);
    public MercadoPagoPixResponseDTO processPixPayment(Long pedidoId, PixDTO pixDto);
}