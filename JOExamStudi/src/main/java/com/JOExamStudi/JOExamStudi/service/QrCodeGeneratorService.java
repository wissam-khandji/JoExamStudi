package com.JOExamStudi.JOExamStudi.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeGeneratorService {

    /**
     * Génère un QR code au format PNG pour un texte donné.
     *
     * @param text Le texte à encoder dans le QR code.
     * @param width La largeur de l'image.
     * @param height La hauteur de l'image.
     * @return Un tableau de bytes représentant l'image PNG du QR code.
     * @throws WriterException En cas d'erreur lors de l'encodage.
     * @throws IOException En cas d'erreur d'écriture dans le flux.
     */
    public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }
}
