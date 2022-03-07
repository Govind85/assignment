package com.banking.assignment.crypto;

import com.banking.assignment.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static com.banking.assignment.constants.GlobalConstants.*;

@Component
@Slf4j
public class CryptoUtil {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    @Value("${cipher.key}")
    private String secret;


    public String encrypt(String strToEncrypt) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("Error while encrypting: ",e.getMessage());
            throw new CustomException(e.getMessage(), ENCRYPT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String decrypt(String strToDecrypt) {
        try {
            prepareSecreteKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
           log.error("Error while decrypting: ", e.getMessage());
           throw new CustomException(e.getMessage(), DECRYPT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void prepareSecreteKey(String myKey)  {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance(SHA_1);
            key = sha.digest(key);
            key = Arrays.copyOf(key, MESSAGE_LENGTH);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error while preparing key: ", e.getMessage());
            throw new CustomException(e.getMessage(), PREPARE_SECRET_KEY, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
