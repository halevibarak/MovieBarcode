package movies.com.ui

import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle

import com.dlazaro66.qrcodereaderview.QRCodeReaderView

import movies.com.dbqr.R

class DecoderActivity : Activity(), QRCodeReaderView.OnQRCodeReadListener {
    private var qrCodeReaderView: QRCodeReaderView? = null
    private var mOnQRCodeRead: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoder)
        qrCodeReaderView = findViewById(R.id.qrdecoderview)
        qrCodeReaderView!!.setOnQRCodeReadListener(this)
        qrCodeReaderView!!.setQRDecodingEnabled(true)
        qrCodeReaderView!!.setAutofocusInterval(2000L)
        qrCodeReaderView!!.setTorchEnabled(true)
        qrCodeReaderView!!.setFrontCamera()
        qrCodeReaderView!!.setBackCamera()
    }

    override fun onQRCodeRead(text: String, points: Array<PointF>) {
        if (mOnQRCodeRead)
            return
        mOnQRCodeRead = true
        val intent = Intent()
        intent.putExtra(FROM_BARCODE, text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        qrCodeReaderView!!.startCamera()
        mOnQRCodeRead = false
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView!!.stopCamera()
    }

    companion object {

        val FROM_BARCODE = "FROM_BARCODE"
    }

}
