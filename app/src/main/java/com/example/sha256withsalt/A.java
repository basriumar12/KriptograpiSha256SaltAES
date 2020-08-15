package com.example.sha256withsalt;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class A {
    public static String doEncrypt(String pesan, String kunci) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(kunci.getBytes(), "AES");
        byte[] bIv = new byte[16];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SecureRandom.getInstanceStrong().nextBytes(bIv);
        }
        IvParameterSpec ivSpec = new IvParameterSpec(bIv);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] bEnc = c.doFinal(pesan.getBytes());
        String strEnc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            strEnc = Base64.getEncoder().encodeToString(bEnc);
        }
        String strIv = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            strIv = Base64.getEncoder().encodeToString(bIv);
        }
        return strIv + ":" + strEnc;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String doDecrypt(String cipher, String kunci) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(kunci.getBytes(), "AES");
        String[] pair = cipher.split(":");
        byte[] bIv = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bIv = Base64.getDecoder().decode(pair[0]);
        }
        byte[] bEnc = Base64.getDecoder().decode(pair[1]);
        IvParameterSpec ivSpec = new IvParameterSpec(bIv);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] bDec = c.doFinal(bEnc);
        return new String(bDec);
    }

}
