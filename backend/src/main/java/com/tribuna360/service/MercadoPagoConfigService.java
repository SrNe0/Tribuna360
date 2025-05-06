package com.tribuna360.service;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MercadoPagoConfigService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @PostConstruct
    public void initializeMercadoPago() {
        MercadoPagoConfig.setAccessToken(accessToken);
         // Reemplaza con el nombre y versión de tu aplicación
    }
}