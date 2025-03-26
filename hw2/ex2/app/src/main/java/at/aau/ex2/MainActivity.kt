package at.aau.ex2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: MainActivity")

        val switchButton = findViewById<Button>(R.id.main_activity_button)
        switchButton.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: MainActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: MainActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: MainActivity")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: MainActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: MainActivity")
    }
}
