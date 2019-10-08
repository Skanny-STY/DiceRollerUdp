package com.betalab.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.charset.Charset
import kotlin.random.Random

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    lateinit var diceImage: ImageView

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val SDK_INT = android.os.Build.VERSION.SDK_INT
         if(SDK_INT > 8) {
             val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                 .permitAll().build()
             StrictMode.setThreadPolicy(policy)
         }

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.text = "Let's Roll"
        rollButton.setOnClickListener {
            //            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
            // rollDice()
            sendStringThroughUdp("Geh Durch mann!")


        }

        diceImage = findViewById(R.id.dice_image)

    }

    fun onViewCreated(view: View, savedInstanceState: Bundle? ){
        val SDK_INT = android.os.Build.VERSION.SDK_INT
        if(SDK_INT > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    @ExperimentalStdlibApi
    private fun sendStringThroughUdp(s:String) {
        val datagramSocket = DatagramSocket(50002)

        val datagramPacket = DatagramPacket(
            s.toByteArray(),
            s.length,
            InetAddress.getLocalHost(),
            50002)

        datagramSocket.send(datagramPacket)
        println("packet sent")




        val buffer = ByteArray(300000)
        val datagramRecPacket = DatagramPacket(buffer,0, buffer.size)
        datagramSocket.receive(datagramRecPacket)

        val messageString =  String(datagramPacket.data, datagramPacket.offset, datagramPacket.length)

        println("received message:  $messageString")

    }

    private fun rollDice() {

        val randomInt = Random.nextInt(6) + 1
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6

        }
        diceImage.setImageResource(drawableResource)

    }


}
