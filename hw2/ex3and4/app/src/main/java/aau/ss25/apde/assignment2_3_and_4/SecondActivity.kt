package aau.ss25.apde.assignment2_3_and_4

import aau.ss25.apde.assignment2_3_and_4.databinding.ActivitySecondBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Ex. 3 Intents
        // val textViewReceived = findViewById<TextView>(R.id.textViewReceived)
        // val buttonGoogleMaps = findViewById<Button>(R.id.buttonGoogleMaps)

        // Ex. 4 View Binding
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedAddress = intent.getStringExtra("address") // fetch data from intent from MainActivity
        binding.textViewReceived.text = receivedAddress

        binding.buttonGoogleMaps.setOnClickListener {
            // From: https://developers.google.com/maps/documentation/urls/android-intents?hl=de#kotlin
            val gmmIntentUri = ("geo:0,0?q=" + Uri.encode(receivedAddress)).toUri() // suggested by IDE instead of Uri.parse
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            mapIntent.setPackage("com.google.android.apps.maps")

            // check if Google Maps / or another app who can receive this intent is actually
            // installed on the device
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }
        }
    }
}