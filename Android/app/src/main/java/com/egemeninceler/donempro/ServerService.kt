package com.egemeninceler.donempro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.DataInputStream
import java.io.IOException
import java.io.PushbackInputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class ServerService: Service(){

    private var serverSocket: ServerSocket? = null
    private val working = AtomicBoolean(true)




    private val runnable = Runnable {

        var socket: Socket? = null
        try {

            serverSocket = ServerSocket(PORT)
            while (working.get()) {
                if (serverSocket != null) {
                    socket = serverSocket!!.accept()
                    Log.i(TAG, "New client: $socket")

                    val dataInputStream = DataInputStream(socket.getInputStream())


                    // Use threads for each client to communicate with them simultaneously
                    var instance = ClientHandler(dataInputStream, baseContext)
                    val t: Thread = Thread(instance)
                    t.start()
                    //var list = instance.getList()
                    //println("liste: $list ${list.size}")
//                    Toast.makeText(applicationContext, list[2] as String, Toast.LENGTH_SHORT).show()

                } else {
                    Log.e(TAG, "Couldn't create ServerSocket!")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            try {
                socket?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }
    override fun onBind(p0: Intent?): IBinder? {
            return null
    }
    override fun onCreate() {
        startMeForeground()
        Thread(runnable).start()
    }
    override fun onDestroy() {
        working.set(false)
    }
    private fun startMeForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val NOTIFICATION_CHANNEL_ID = packageName
            val channelName = "Tcp Server Background Service"
            val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            manager.createNotificationChannel(chan)
            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tcp Server is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
            startForeground(2, notification)
        } else {
            startForeground(1, Notification())
        }
    }

    companion object {
        private val TAG = ServerService::class.java.simpleName
        private const val PORT = 12400
    }
}