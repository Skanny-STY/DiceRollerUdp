package com.betalab.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.NumberFormatException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var ipaddress: EditText
    lateinit var message: EditText
    lateinit var port: EditText
    val datagramSocket = DatagramSocket(50002)

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val SDK_INT = android.os.Build.VERSION.SDK_INT
         if(SDK_INT > 8) {
             val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                 .permitAll().build()
             StrictMode.setThreadPolicy(policy)
         }

         ipaddress = findViewById(R.id.ipAddress_editText)
         message = findViewById(R.id.message_editText)
         port= findViewById(R.id.port_editText)

        val sendButton: Button = findViewById(R.id.roll_button)
        sendButton.text = "Send Message"

        sendButton.setOnClickListener {
//             Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
//             rollDice()
            sendStringThroughUdp(message.text.toString())


        }




    }

    private fun sendStringThroughUdp(message:String) {

        try{
            val ip = InetAddress.getByName(ipaddress.text.toString())
            val portnumber = Integer.parseInt(port.text.toString())
            val datagramPacket = DatagramPacket(
                message.toByteArray(),
                message.length,
                ip,
               portnumber)

            datagramSocket.send(datagramPacket)
            println("packet sent")

            val buffer = ByteArray(3000)
            val datagramRecPacket = DatagramPacket(buffer,0, buffer.size)

            datagramSocket.receive(datagramRecPacket)
            Toast.makeText(this,"message Sent",Toast.LENGTH_SHORT).show()
            val messageString =  String(datagramPacket.data, datagramPacket.offset, datagramPacket.length)

            println("received message:  $messageString")
        } catch(e: UnknownHostException){
            e.printStackTrace()
            Toast.makeText(this,"unknown Ip",Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException){
            e.printStackTrace()
            Toast.makeText(this,"port is not a number",Toast.LENGTH_SHORT).show()
        }










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

    }


}
