package com.tribuna360.service;

import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    public String crearPreferenciaDePago(String nombreAbono, BigDecimal precio, String emailUsuario, String idAbono) throws MPException, MPApiException {
        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(nombreAbono) // Usamos nombreAbono aquí
                .quantity(1)
                .currencyId("COP") // Cambia a la moneda que necesites
                .unitPrice(precio)
                .build();
        items.add(item);

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(emailUsuario)
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                // Aquí configuraremos la URL de notificación (webhook) y las URLs de retorno (opcional)
                .metadata(java.util.Map.of("idAbono", idAbono)) // Puedes pasar información adicional en los metadatos
                .build();

        Preference preference = client.create(request);
        return preference.getInitPoint(); // URL a la que redirigir al usuario para pagar
    }
}