package at.aau.ex2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OtherActivity : AppCompatActivity() {
    val TAG: String = "OtherActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_activity)
        Log.d(TAG, "onCreate: OtherActivity")

        val switchButton = findViewById<Button>(R.id.other_switch_button)
        switchButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: OtherActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: OtherActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: OtherActivity")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: OtherActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: OtherActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: OtherActivity")
    }
}