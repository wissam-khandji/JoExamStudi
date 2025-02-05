package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.service.QrCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcode")
public class QrCodeController {

    @Autowired
    private QrCodeGeneratorService qrCodeGeneratorService;

    /**
     * Endpoint GET pour générer un QR code à partir du texte fourni en paramètre.
     * Exemple d'utilisation : /api/qrcode?text=HelloWorld
     *
     * @param text Le texte à encoder dans le QR code (valeur par défaut si non fourni).
     * @return L'image PNG du QR code.
     */
    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCode(@RequestParam(value = "text", defaultValue = "Default QR Code") String text) {
        try {
            byte[] qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(text, 200, 200);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
