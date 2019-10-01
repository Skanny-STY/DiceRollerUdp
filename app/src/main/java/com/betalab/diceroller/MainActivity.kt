package com.betalab.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.text = "Let's Roll"
        rollButton.setOnClickListener {
            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()

            val datagramSocket: DatagramSocket = DatagramSocket(50001)
            val message = "geh Durch oida"

            val datagramPacket = DatagramPacket(
                message.toByteArray(),
                message.length,
                InetAddress.getLocalHost(),
                50001)

            datagramSocket.send(datagramPacket)
        }

    }
}
