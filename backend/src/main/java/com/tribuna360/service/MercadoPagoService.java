package com.tribuna360.service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    public String crearPreferenciaDePago(String nombreAbono, BigDecimal precio, String emailUsuario, String idAbono, Long idUsuario, com.tribuna360.model.Abono abono) throws MPException, MPApiException {
        PreferenceClient client = new PreferenceClient();

        // Crear el ítem de la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .id(String.valueOf(abono.getIdAbono())) // Usar el ID del abono
                .title(nombreAbono)
                .description("Abono para " + abono.getClub().getNombre() + " - " + abono.getEstadio().getNombre()) // Ejemplo de descripción
                //.pictureUrl("URL_DE_LA_IMAGEN") // Añade la URL de la imagen si tienes una
                .categoryId("sports") // Categoría genérica
                .quantity(1)
                .currencyId("COP")
                .unitPrice(precio)
                .build();
        items.add(item);

        // Crear el pagador con el email del usuario
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(emailUsuario)
                .build();

        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("https://www.seu-site/success")
                        .pending("https://www.seu-site/pending")
                        .failure("https://www.seu-site/failure")
                        .build();

        // Crear la preferencia de pago
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .backUrls(backUrls)
                .notificationUrl("https://webhook.site/1601ef91-88ef-429e-9641-155a008f73bd") // Reemplazar con tu URL real
                .metadata(Map.of("idAbono", idAbono, "idUsuario", String.valueOf(idUsuario))) // Incluir idUsuario en metadata
                .build();

        // Crear la preferencia en MercadoPago
        Preference preference = client.create(request);

        // Retornar la URL a la que el usuario debe ir para completar el pago
        return preference.getInitPoint(); // Usar getInitPoint() en producción
    }

    public Payment obtenerDetallePago(String paymentId) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();
        // Convertir paymentId a Long
        return client.get(Long.parseLong(paymentId));
    }
}