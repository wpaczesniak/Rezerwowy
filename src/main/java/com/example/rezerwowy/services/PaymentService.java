package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final int TAX_IN_PERCENT = 32;
    private static final int RECEIPT_LINE_LENGTH = 64;

    private final PaymentRepository paymentRepository;

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public Payment getPaymentByPublicId(UUID paymentPublicId) {
        return paymentRepository.findByPublicId(paymentPublicId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentPublicId));
    }

    public String generatePaymentReceipt(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        int seatsNumber = 1; // TODO
        BigDecimal pricePerSeat = BigDecimal.valueOf(10); // TODO
        BigDecimal totalAmount = calculateTotalAmount(seatsNumber, pricePerSeat);
        BigDecimal taxTotalAmount = calculateTax(totalAmount);
        String seatsList = generateSeatsList(seatsNumber, pricePerSeat);

        return buildReceipt(payment, seatsList, taxTotalAmount, totalAmount);
    }

    private BigDecimal calculateTotalAmount(int seatsNumber, BigDecimal pricePerSeat) {
        return pricePerSeat.multiply(BigDecimal.valueOf(seatsNumber));
    }

    private String createLineBreak() {
        return String.join(" ", Collections.nCopies(RECEIPT_LINE_LENGTH / 2, "-"));
    }

    private String generateSeatsList(int seatsNumber, BigDecimal pricePerSeat) {
        return String.join("\n", Collections.nCopies(
                seatsNumber,
                alignTextToRightAndLeftAtReceipt("MIEJSCE", pricePerSeat.toString())
        ));
    }

    private String buildReceipt(Payment payment, String seatsList, BigDecimal taxTotalAmount, BigDecimal totalAmount) {
        String lineBreak = createLineBreak();

        return centerTextAtReceipt("REZERWOWY") + "\n" +
                "\n" +
                centerTextAtReceipt("PARAGON: " + payment.getPublicId()) + "\n" +
                centerTextAtReceipt("DATA: " + payment.getDate()) + "\n" +
                "KASJER: KASA ONLINE\n" +
                lineBreak +
                centerTextAtReceipt("PARAGON FISKALNY") +
                lineBreak +
                seatsList +
                lineBreak +
                alignTextToRightAndLeftAtReceipt("PODATEK " + TAX_IN_PERCENT + "%", taxTotalAmount.toString()) +
                lineBreak +
                alignTextToRightAndLeftAtReceipt("SUMA: ", totalAmount.toString());
    }

    private BigDecimal calculateTax(BigDecimal totalAmount) {
        return totalAmount.multiply(BigDecimal.valueOf(TAX_IN_PERCENT));
    }

    private String centerTextAtReceipt(String text) {
        int spacesCount = text.length() - RECEIPT_LINE_LENGTH;
        int leftSpacesCount = spacesCount / 2;
        int rightSpacesCount = spacesCount - leftSpacesCount;

        return String.format("%" + leftSpacesCount + "s%s%" + rightSpacesCount + "s", "", text, "");
    }

    private String alignTextToRightAndLeftAtReceipt(String leftText, String rightText) {
        int spacesCount = RECEIPT_LINE_LENGTH - leftText.length() - rightText.length();

        return String.format("%s%" + spacesCount + "s%s", leftText, "", rightText);
    }
}
