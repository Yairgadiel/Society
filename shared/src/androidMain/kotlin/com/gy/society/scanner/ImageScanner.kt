package com.gy.society.scanner

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

actual class ImageScanner {
    actual fun scanImageForResult(buffer: ByteArray, callback: IOnImageScannedListener) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val image = InputImage.fromByteArray(
            buffer,
            /* image width */ 120,
            /* image height */ 160,
            0,
            InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
        )

        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                callback.onImageScanned(visionText.text)
            }
            .addOnFailureListener { e ->
                print(e.stackTrace)
                callback.onImageScanned(e.message)
            }
    }
}