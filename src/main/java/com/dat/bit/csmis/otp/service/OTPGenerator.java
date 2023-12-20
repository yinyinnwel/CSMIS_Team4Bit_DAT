package com.dat.bit.csmis.otp.service;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class OTPGenerator {

    private static final int DIGITS = 6; // number of digits in the OTP
    private static final int TIME_STEP_SECONDS = 30; // time step in seconds
    private static final String HMAC_ALGORITHM = "HmacSHA1"; // HMAC algorithm to use

    private SecretKey secretKey;

    public OTPGenerator() throws NoSuchAlgorithmException {
        // generate a new secret key using the HMAC algorithm
        KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_ALGORITHM);
        secretKey = keyGenerator.generateKey();
    }

    public String generateOTP() throws InvalidKeyException, NoSuchAlgorithmException {
        // calculate the counter value based on the current time
        long counter = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;

        // encode the counter value in a byte array
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(counter);
        byte[] counterBytes = buffer.array();

        // calculate the HMAC-SHA1 hash of the counter value using the secret key
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKey);
        byte[] hashBytes = mac.doFinal(counterBytes);

        // truncate the hash value to the desired number of digits
        int offset = hashBytes[hashBytes.length - 1] & 0xf; // use the last 4 bits as an offset
        int truncatedHash = ((hashBytes[offset] & 0x7f) << 24) | ((hashBytes[offset + 1] & 0xff) << 16) | ((hashBytes[offset + 2] & 0xff) << 8) | (hashBytes[offset + 3] & 0xff);
        truncatedHash %= Math.pow(10, DIGITS);

        // pad the truncated hash value with zeros to the desired number of digits
        String otp = String.format("%0" + DIGITS + "d", truncatedHash);

        return otp;
    }

//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
//        OTPGenerator otpGenerator = new OTPGenerator();
//        String otp = otpGenerator.generateOTP();
//        System.out.println("OTP: " + otp);
//    }
}
