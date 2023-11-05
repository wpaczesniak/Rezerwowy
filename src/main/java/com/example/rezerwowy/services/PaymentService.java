package com.example.rezerwowy.services;

import com.example.rezerwowy.exceptions.PaymentAlreadyExistsException;
import com.example.rezerwowy.exceptions.PaymentNotFoundException;
import com.example.rezerwowy.models.Payment;
import com.example.rezerwowy.repositories.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public static final double TAX_IN_PERCENT = 23;
    public static final int RECEIPT_LINE_LENGTH = 64;

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(@Valid Payment payment) {
        if (payment.getId() != null && paymentRepository.existsById(payment.getId()))
            throw new PaymentAlreadyExistsException(payment.getId());
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    @Transactional
    public void deletePaymentById(Long paymentId) {
        if (!paymentRepository.existsById(paymentId))
            throw new PaymentNotFoundException(paymentId);
        paymentRepository.deleteById(paymentId);
    }

    @Transactional
    public String generatePaymentReceipt(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        int seatsCount = getSeatsCount(payment);
//        int seatsCount = 2;
        Money pricePerSeat = getPricePerSeat(payment);
//        Money pricePerSeat = Money.of(10.00, "PLN");
        Money totalAmount = calculateTotalAmount(seatsCount, pricePerSeat);
        Money taxTotalAmount = calculateTax(totalAmount);
        String seatsList = generateSeatsList(seatsCount, pricePerSeat);

        return buildReceipt(payment, seatsList, taxTotalAmount, totalAmount);
    }

    private int getSeatsCount(Payment payment) {
        return payment.getReservation().getSeats().size();
    }

    private Money getPricePerSeat(Payment payment) {
        return payment.getReservation().getFootballMatch().getPricePerSeat();
    }

    private Money calculateTotalAmount(int seatsCount, Money pricePerSeat) {
        return pricePerSeat.multiply(seatsCount);
    }

    private Money calculateTax(Money totalAmount) {
        return totalAmount.multiply(TAX_IN_PERCENT).divide(100);
    }

    private String createLineBreak() {
        return String.join(" ", Collections.nCopies(RECEIPT_LINE_LENGTH / 2, "-")) + " ";
    }

    private String generateSeatsList(int seatsCount, Money pricePerSeat) {
        return String.join("\n", Collections.nCopies(
                seatsCount,
                alignTextToRightAndLeftAtReceipt("MIEJSCE X1", pricePerSeat.toString())
        ));
    }

    private String buildReceipt(Payment payment, String seatsList, Money taxTotalAmount, Money totalAmount) {
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
