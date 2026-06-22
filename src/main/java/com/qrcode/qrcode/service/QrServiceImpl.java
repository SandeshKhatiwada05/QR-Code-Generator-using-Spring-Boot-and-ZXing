package com.qrcode.qrcode.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class QrServiceImpl implements QrService {
    @Override
    public byte[] generateQR(String text) throws WriterException, IOException {
        log.info("generateQR() START: Text {}", text);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().fillRect(0, 0, 200, 200);

        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(Color.black);

        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics2D.fillRect(x, y, 1, 1);
                }
            }
        }
        log.info("Color coded in qr");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) bufferedImage, "png", byteStream);

        log.info("generateQR() COMPLETE");
        return byteStream.toByteArray();
    }

    @Override
    public String readQR(byte[] qrImage) throws IOException {
        log.info("readeQR START");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(qrImage);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        HybridBinarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        MultiFormatReader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(binaryBitmap);
            String resultText = result.getText();
            log.info("readeQR COMPLETE with text {}", resultText);
            return resultText;
        } catch (ReaderException ex) {
            log.error("Error Found During QR Reading process", ex);
            return "Error Found while Reading qr";
        }
    }
}
