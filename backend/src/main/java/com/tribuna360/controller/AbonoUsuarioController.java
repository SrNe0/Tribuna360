package com.tribuna360.controller;

import com.tribuna360.model.Abono;
import com.tribuna360.model.AbonoUsuario;
import com.tribuna360.model.Usuario;
import com.tribuna360.service.AbonoService;
import com.tribuna360.service.AbonoUsuarioService;
import com.tribuna360.service.MercadoPagoService;
import com.tribuna360.service.UsuarioService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Map;

@RestController
@RequestMapping("/api/abono-usuarios")
public class AbonoUsuarioController {

    private final MercadoPagoService mercadoPagoService;
    private final AbonoService abonoService;
    private final UsuarioService usuarioService;
    private final AbonoUsuarioService abonoUsuarioService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AbonoUsuarioController(MercadoPagoService mercadoPagoService, AbonoService abonoService, UsuarioService usuarioService, AbonoUsuarioService abonoUsuarioService, ObjectMapper objectMapper) {
        this.mercadoPagoService = mercadoPagoService;
        this.abonoService = abonoService;
        this.usuarioService = usuarioService;
        this.abonoUsuarioService = abonoUsuarioService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/procesar-pago")
    public ResponseEntity<String> procesarPagoManual(@RequestBody Map<String, String> payload) {
        String paymentId = payload.get("paymentId");
        if (paymentId == null || paymentId.isEmpty()) {
            return new ResponseEntity<>("Debe proporcionar el paymentId", HttpStatus.BAD_REQUEST);
        }

        try {
            Payment paymentDetails = mercadoPagoService.obtenerDetallePago(paymentId);

            if (paymentDetails != null && "approved".equalsIgnoreCase(paymentDetails.getStatus())) {
                JsonNode metadata = objectMapper.readTree(objectMapper.writeValueAsString(paymentDetails.getMetadata()));
                String abonoIdStr = metadata.get("id_abono").asText();
                String usuarioIdStr = metadata.get("id_usuario").asText();
                String paymentMethodId = paymentDetails.getPaymentMethodId();
                BigDecimal montoPagado = new BigDecimal(paymentDetails.getTransactionAmount().toString());
                LocalDate fechaCompra = paymentDetails.getDateApproved().toLocalDate();
                Long idAbono = Long.parseLong(abonoIdStr);
                Long idUsuario = Long.parseLong(usuarioIdStr);
                Abono abono = abonoService.obtenerAbonoPorId(idAbono);
                Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

                if (abono != null && usuario != null) {
                    AbonoUsuario abonoUsuario = new AbonoUsuario();
                    abonoUsuario.setAbono(abono);
                    abonoUsuario.setUsuario(usuario);
                    abonoUsuario.setFechaCompra(fechaCompra);
                    abonoUsuario.setEstado("activo");
                    abonoUsuario.setIdPago(paymentDetails.getId().toString());
                    abonoUsuario.setMetodoPago(paymentMethodId);
                    abonoUsuario.setMontoPagado(montoPagado);
                    abonoUsuario.setEstadoPago(paymentDetails.getStatus());
                    abonoUsuario.setUbicacion(abono.getUbicacion());

                    abonoUsuarioService.guardarAbonoUsuario(abonoUsuario);

                    return ResponseEntity.ok("Pago procesado y AbonoUsuario creado.");
                } else {
                    return new ResponseEntity<>("No se encontró el Abono o el Usuario", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("El pago no fue aprobado", HttpStatus.BAD_REQUEST);
            }

        } catch (MPException | MPApiException e) {
            System.err.println("Error al obtener detalles del pago: " + e.getMessage());
            return new ResponseEntity<>("Error al obtener detalles del pago de Mercado Pago", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            System.err.println("Error al procesar los detalles del pago: " + e.getMessage());
            return new ResponseEntity<>("Error interno al procesar la respuesta de Mercado Pago", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}