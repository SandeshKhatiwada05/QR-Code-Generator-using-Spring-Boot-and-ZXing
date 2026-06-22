# QR Code Generator & Reader

A Spring Boot REST API for generating and decoding QR codes using the [ZXing](https://github.com/zxing/zxing) library.

## Features

- **Generate** QR codes from text — returns a PNG image
- **Read** / decode QR codes from uploaded PNG or JPEG images

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/qr` | Generate a QR code. Send `text` as a form parameter. |
| `GET`  | `/qr` | Read a QR code. Send `file` as a multipart upload. |

## Tech Stack

- Java 17 + Spring Boot
- ZXing 3.5.3
- Maven
- Lombok

## Run

```bash
./mvnw spring-boot:run
```

## Example Usage

**Generate a QR code:**

```bash
curl -X POST http://localhost:8080/qr -d "text=Hello World"
```

**Read a QR code:**

```bash
curl -X GET http://localhost:8080/qr -F "file=@qrcode.png"
```
