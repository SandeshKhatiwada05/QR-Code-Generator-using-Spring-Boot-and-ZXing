package com.qrcode.qrcode.controller;

import com.google.zxing.WriterException;
import com.qrcode.qrcode.dto.QRCodeDto;
import com.qrcode.qrcode.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QRController {
    private final QrService qrService;

    @PostMapping
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeDto qrCodeDto) throws IOException, WriterException {
        byte[] qrCode = qrService.generateQR(qrCodeDto.getText());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(qrCode);
    }
}
