package movies.com.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import movies.com.dbqr.R;

public class DecoderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    public static final String FROM_BARCODE = "FROM_BARCODE";
    private QRCodeReaderView qrCodeReaderView;
    private boolean mOnQRCodeRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        qrCodeReaderView = findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setTorchEnabled(true);
        qrCodeReaderView.setFrontCamera();
        qrCodeReaderView.setBackCamera();
    }
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (mOnQRCodeRead)
            return;
        mOnQRCodeRead = true;
        Intent intent = new Intent();
        intent.putExtra(FROM_BARCODE, text);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
        mOnQRCodeRead = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

}
