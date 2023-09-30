package com.devandroid.appqrcodegenarator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class MainActivity extends AppCompatActivity {
    EditText editQrCode;
    ImageView qrCodeImage;
    Button btnGenarateQr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        btnGenarateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editQrCode.getText().toString())){
                    editQrCode.setError("Empty");
                    editQrCode.requestFocus();
                }else{
                    genareteQrCode(editQrCode.getText().toString());
                }
            }
        });
    }

    private void genareteQrCode(String qrCodeContent) {
        //zxing-android-embedded -> lib to genarate qrCode

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try{
            BitMatrix matrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE,196,196);

            int width = matrix.getWidth();
            int height = matrix.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);

            for (int w = 0; w < width; w++){
                for (int h = 0; h < height; h++){
                    bitmap.setPixel(w,h,matrix.get(w,h) ? Color.BLACK : Color.WHITE);
                }
            }

            qrCodeImage.setImageBitmap(bitmap);

        }catch(WriterException e){
            e.printStackTrace();
        }
    }

    private void initComponents() {
        editQrCode = findViewById(R.id.editQrCode);
        qrCodeImage = findViewById(R.id.imageQrcode);
        btnGenarateQr = findViewById(R.id.btnGenarateQrCode);
    }
}