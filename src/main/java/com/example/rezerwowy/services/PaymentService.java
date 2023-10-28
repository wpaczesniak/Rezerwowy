package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final int TAX_IN_PERCENT = 23;
    private static final int RECEIPT_LINE_LENGTH = 64;

    private final PaymentRepository paymentRepository;

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    public void deletePaymentById(Long paymentId) {
        if (!paymentRepository.existsById(paymentId))
            throw new PaymentNotFoundException(paymentId);
        paymentRepository.deleteById(paymentId);
    }

    public Payment getPaymentByPublicId(UUID paymentPublicId) {
        return paymentRepository.findByPublicId(paymentPublicId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentPublicId));
    }

    public void deletePaymentByPublicId(UUID paymentPublicId) {
        if (!paymentRepository.existsByPublicId(paymentPublicId))
            throw new PaymentNotFoundException(paymentPublicId);
        paymentRepository.deleteByPublicId(paymentPublicId);
    }

    public String generatePaymentReceipt(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        int seatsNumber = 1; // TODO
        BigDecimal pricePerSeat = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP); // TODO
        BigDecimal totalAmount = calculateTotalAmount(seatsNumber, pricePerSeat);
        BigDecimal taxTotalAmount = calculateTax(totalAmount);
        String seatsList = generateSeatsList(seatsNumber, pricePerSeat);

        return buildReceipt(payment, seatsList, taxTotalAmount, totalAmount);
    }

    private BigDecimal calculateTotalAmount(int seatsNumber, BigDecimal pricePerSeat) {
        return pricePerSeat.multiply(BigDecimal.valueOf(seatsNumber));
    }

    private BigDecimal calculateTax(BigDecimal totalAmount) {
        BigDecimal taxRate = BigDecimal.valueOf(TAX_IN_PERCENT)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return totalAmount.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    private String createLineBreak() {
        return String.join(" ", Collections.nCopies(RECEIPT_LINE_LENGTH / 2, "-"));
    }

    private String generateSeatsList(int seatsNumber, BigDecimal pricePerSeat) {
        return String.join("\n", Collections.nCopies(
                seatsNumber,
                alignTextToRightAndLeftAtReceipt("MIEJSCE X1", pricePerSeat.toString())
        ));
    }

    private String buildReceipt(Payment payment, String seatsList, BigDecimal taxTotalAmount, BigDecimal totalAmount) {
        String lineBreak = createLineBreak();

        return centerTextAtReceipt("REZERWOWY") + "\n" +
                "\n" +
                centerTextAtReceipt("PARAGON: " + payment.getPublicId()) + "\n" +
                centerTextAtReceipt("DATA: " + payment.getDate()) + "\n" +
                centerTextAtReceipt("KASJER: KASA ONLINE") + "\n" +
                lineBreak + "\n" +
                centerTextAtReceipt("PARAGON FISKALNY") + "\n" +
                lineBreak + "\n" +
                seatsList + "\n" +
                lineBreak + "\n" +
                alignTextToRightAndLeftAtReceipt(
                        "PODATEK " + TAX_IN_PERCENT + "%", taxTotalAmount.toString()
                ) + "\n" +
                lineBreak + "\n" +
                alignTextToRightAndLeftAtReceipt("SUMA: ", totalAmount.toString());
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
