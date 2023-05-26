package ru.mirea.malyushin.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    byte[] cryptCode;
    byte[] key;
    SecretKey originalKey;
    String finalStr;
    public static final String ARG_WORD = "word";
    public static final String KEY = "key";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
//            firstName = args.getString(ARG_WORD);
            cryptCode = args.getByteArray(ARG_WORD);
            key = args.getByteArray(KEY);
            originalKey = new SecretKeySpec(key, 0, key.length, "AES");
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
// emulate long-running operation
        SystemClock.sleep(5000);
        finalStr = decryptMsg(cryptCode, originalKey);
        return finalStr;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        /* Decrypt the message */
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}


