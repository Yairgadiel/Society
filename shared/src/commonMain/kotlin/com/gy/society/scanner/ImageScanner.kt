package com.gy.society.scanner

expect class ImageScanner {
    fun scanImageForResult(buffer: ByteArray, callback: IOnImageScannedListener)
}