package com.example.bezbednostbackend.encryption;

import com.example.bezbednostbackend.model.User;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;

@Service
public class EncryptionService {
    private static final String ENCRYPTION_KEY = System.getenv("ENCRYPTION_KEY");

    public String encryptData(String data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encryptedData = EncryptionUtils.encrypt(data.getBytes(), ENCRYPTION_KEY);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decryptData(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = EncryptionUtils.decrypt(decodedData, ENCRYPTION_KEY);
        return new String(decryptedData);
    }

    public User encryptConfidentialUserData(User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        user.setName(encryptData(user.getName()));
        user.setSurname(encryptData(user.getSurname()));
        user.setPhoneNumber(encryptData(user.getPhoneNumber()));
        return user;
    }
}
