package ru.mirea.malyushin.cryptoloader;

//import android.app.LoaderManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.malyushin.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    private ActivityMainBinding binding;
    public final String TAG = this.getClass().getSimpleName();
    private final int LoaderID = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        SecretKey secretCode = generateKey();
        bundle.putByteArray(MyLoader.ARG_WORD,
                encryptMsg(String.valueOf(binding.editText.getText()), secretCode));
        bundle.putByteArray(MyLoader.KEY, secretCode.getEncoded());
        Log.i(TAG, secretCode.toString());
        LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this);
    }

    @NonNull
    @Override
    public androidx.loader.content.Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader: wait...", Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        throw new InvalidParameterException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<String> loader, String s) {
        if (loader.getId() == LoaderID) {
            Log.d(TAG, "onLoadFinished: " + s);
            Toast.makeText(this, "onLoadFinished: " + s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    public static SecretKey generateKey(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
}