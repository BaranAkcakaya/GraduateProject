package com.egemeninceler.donempro

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egemeninceler.donempro.ViewModel.MainViewModel
import com.egemeninceler.donempro.util.rotate90FImage
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.net.Socket

class Client(address: String, port: Int){
    var connection: Socket = Socket(address, port)
    var clientHandler = ClientHandler()

    init {

        //println("Connected to server at $address port $port"
    }


    fun write(bytes: ByteArray) {
        try {

            connection.soTimeout = 10000
            var writer: OutputStream = connection.getOutputStream()
            val baos = ByteArrayOutputStream()
//        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val bitmap = rotate90FImage(bytes)
            resizeImage(bitmap!!).compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val byteArray = baos.toByteArray()


            Thread.sleep(250)
            writer.write(byteArray)
            writer.write("sended".toByteArray())
            //clientHandler.returnValue()
            writer.flush()
            baos.flush()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun resizeImage(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 480, 600, true)

    }

    fun shutdown() {

        if (connection.isConnected) {
            connection.close()
        }

    }
}