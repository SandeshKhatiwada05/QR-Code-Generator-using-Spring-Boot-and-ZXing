package com.qrcode.qrcode.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QrService {
    byte[] generateQR(String text) throws WriterException, IOException;

    String readQR(byte[] qrImage) throws IOException;
}
