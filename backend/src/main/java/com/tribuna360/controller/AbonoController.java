package com.tribuna360.controller;

import com.tribuna360.model.Abono;
import com.tribuna360.model.Usuario;
import com.tribuna360.security.CustomUserDetails;
import com.tribuna360.service.AbonoService;
import com.tribuna360.service.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/abonos")
public class AbonoController {

    private final AbonoService abonoService;
    private final MercadoPagoService mercadoPagoService;

    @Autowired
    public AbonoController(AbonoService abonoService, MercadoPagoService mercadoPagoService) {
        this.abonoService = abonoService;
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/comprar")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> comprarAbono(@RequestBody Map<String, Long> payload) {
        System.out.println("Payload recibido: " + payload); // Paso 1
        Long idAbono = payload.get("idAbono");
        System.out.println("Valor de payload.get(\"idAbono\"): " + idAbono); // Paso 2
        Abono abono = abonoService.obtenerAbonoPorId(idAbono);
        System.out.println("Valor de idAbono antes de llamar al servicio: " + idAbono); // Paso 3
        if (abono == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = null;
        Long idUsuario = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            Usuario usuario = userDetails.getUsuario();
            emailUsuario = usuario.getEmail();
            idUsuario = usuario.getIdUsuario(); // Obtener el idUsuario
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Construir el nombre del abono para la descripción en Mercado Pago
        String nombreAbono = "Abono Club " + abono.getClub().getNombre() +
                " - Estadio " + abono.getEstadio().getNombre() + " (" + abono.getEstadio().getUbicacion() + ")" +
                " - Ubicación " + abono.getUbicacion().getZona();

        try {
            System.out.println("Nombre del abono: " + nombreAbono);
            System.out.println("Precio del abono: " + abono.getPrecio());
            System.out.println("Email del usuario: " + emailUsuario);
            System.out.println("ID del abono: " + abono.getIdAbono());
            System.out.println("ID del usuario: " + idUsuario); // Imprimir el idUsuario

            String initPoint = mercadoPagoService.crearPreferenciaDePago(
                    nombreAbono,
                    abono.getPrecio(),
                    emailUsuario,
                    String.valueOf(abono.getIdAbono()),
                    idUsuario, // Pasar el idUsuario
                    abono
            );
            return ResponseEntity.ok(Map.of("initPoint", initPoint));
        } catch (com.mercadopago.exceptions.MPApiException e) {
            // Loguea el cuerpo de la respuesta de error de Mercado Pago
            System.err.println("Error de la API de Mercado Pago: " + e.getApiResponse().getContent());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (com.mercadopago.exceptions.MPException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para crear un nuevo abono (solo ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Abono crearAbono(@RequestBody Abono abono) {
        return abonoService.crearAbono(abono);
    }

    // Endpoint para obtener un abono por ID (accesible para ADMIN o USER)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Abono obtenerAbonoPorId(@PathVariable Long id) {
        return abonoService.obtenerAbonoPorId(id);
    }

    // Endpoint para eliminar un abono por ID (solo ADMIN)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarAbono(@PathVariable Long id) {
        abonoService.eliminarAbono(id);
    }

    // Endpoint para obtener los abonos disponibles por club (accesible para USER o ADMIN)
    @GetMapping("/club/{clubId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Abono> obtenerAbonosPorClub(@PathVariable Long clubId) {
        return abonoService.obtenerAbonosPorClub(clubId);
    }

    // Endpoint para obtener los abonos adquiridos por el usuario (accesible para USER o ADMIN)
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Abono> obtenerAbonosPorUsuario(@PathVariable Long usuarioId) {
        return abonoService.obtenerAbonosPorUsuario(usuarioId);
    }
}