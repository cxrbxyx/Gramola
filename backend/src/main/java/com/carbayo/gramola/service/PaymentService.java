package com.carbayo.gramola.service;

import com.carbayo.gramola.model.StripeTransaction;
import com.carbayo.gramola.repository.StripeTransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // API Key de prueba de Stripe (según documentación de la práctica)
    static {
        Stripe.apiKey = "";
        // NOTA: Usa tu propia Secret Key de Stripe si tienes una cuenta creada, sino
        // esta es un placeholder.
        // En un entorno real, esto iría en application.properties.
    }

    @Autowired
    private StripeTransactionRepository transactionRepository;

    public StripeTransaction prepay(String userEmail, Long amount) throws StripeException {

        // 1. Configurar parámetros del intento de pago (en céntimos)
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCurrency("eur")
                .setAmount(amount) // Ejemplo: 1000 céntimos = 10.00€
                .build();

        // 2. Crear el PaymentIntent en Stripe
        PaymentIntent intent = PaymentIntent.create(params);

        // 3. Guardar la transacción en nuestra BD
        StripeTransaction transaction = new StripeTransaction();
        transaction.setAmount(amount);
        transaction.setCurrency("eur");
        transaction.setUserEmail(userEmail);
        transaction.setData(intent.toJson()); // Guardamos el JSON completo para referencia

        return transactionRepository.save(transaction);
    }

    public void confirmPayment(String transactionId) {
        // Aquí podríamos añadir lógica adicional, como marcar al usuario como "pagado"
        // o "activo"
        // Por ahora, solo verificamos que la transacción existe.
        transactionRepository.findById(transactionId).ifPresent(t -> {
            // Lógica de confirmación (ej. activar usuario)
        });
    }
}