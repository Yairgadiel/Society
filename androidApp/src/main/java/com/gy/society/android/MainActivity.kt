package com.gy.society.android

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import com.gy.society.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gy.society.scanner.IOnImageScannedListener
import com.gy.society.scanner.ImageScanner
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        val rv : RecyclerView = findViewById(R.id.rv)

        val colNum = resources.getInteger(R.integer.columns_num)

        rv.layoutManager = GridLayoutManager(this, colNum)
        rv.adapter = ItemAdapter(listOf(
            Item("yair", 5),
            Item("keren", 15),
            Item("volov", 2),
            Item("kirby", 0),
            Item("yair2", 5),
            Item("keren2", 15),
            Item("volov2", 2),
            Item("kirby2", 0),
            Item("keren <3", 3)
        ))

        val captureBtn: ImageButton = findViewById(R.id.capture_image)
        captureBtn.setOnClickListener { dispatchTakePictureIntent() }

    }




    // region image

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)

        val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

        if (hasLowStorage) {
            throw IllegalStateException()
        }

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            ImageScanner().scanImageForResult(stream.toByteArray(),
                object : IOnImageScannedListener {
                    override fun onImageScanned(scannedText: String?) {
                        Log.d("TAG", scannedText ?: "No text scannedWS")
                            Toast.makeText(this@MainActivity, scannedText, Toast.LENGTH_LONG)
                                .show()
                    }
                })
        }
        else {
            Log.e("TAG", "no result")
        }
    }

    // endregion
}
