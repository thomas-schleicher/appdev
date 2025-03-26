package aau.ss25.apde.assignment2_3_and_4

import aau.ss25.apde.assignment2_3_and_4.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ex. 3 Intents
        // val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        // val buttonSend      = findViewById<Button>(R.id.buttonSend)

        // Ex. 4 View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonSend.setOnClickListener {
            val address = binding.editTextAddress.text.toString()

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("address", address) // transfer data to SecondActivity
            startActivity(intent)
        }
    }
}

/**
 * Ex. 3 Intents - 3.5
 * ===================
 * Differences Explicit / Implicit Intents:
 *  Explicit Intents:
 *      are used to start a component in an app (e.g., a new activity or a service)
 *      specify which application will satisfy it
 *          by supplying target's app package name or a fully-classified class name
 *  Implicit Intents:
 *      do not name a specific component
 *      specify a specific action to perform
 *          that can be performed by a component from another app
 *          for example, show a map location (by an app than can show maps)
 *
 * Source: Lecture Slides
 */

/**
 * Ex. 4 View Binding - 4.2, 4.3
 * =============================
 * Main Steps:
 *  1. build.gradle.kts -> activate viewBinding in buildFeatures
 *  2. Sync Project with Gradle Files
 *  3. Automatically generate Binding Classes from activity_xyz.xml
 *  4. Inflate Layout with .inflate(...)
 *  5. Access to views via binding.xyz
 *
 * Differences / Pros & Cons
 * +----------------------------------------------------------------------------------------------+
 * | Comparison        | findViewById                        | View Binding                       |
 * +----------------------------------------------------------------------------------------------+
 * | Type-Safety       | (C) manual casting (e.g. as Button) | (P) direct (e.g. binding.buttonXY) |
 * | Null-Safety       | (C) can be null                     | (P) only valid views available     |
 * | Error-Possibility | (C) human fault (e.g. Cast-Error)   | (P) low (Compiler)                 |
 * | Setup             | (P) no setup                        | (C) has to be activated in Gradle  |
 * +----------------------------------------------------------------------------------------------+
 * Researched using ChatGPT 4o on 26.03.2025 16:30
 */

/**
 * Ex. 4 View Binding - Bonus
 * ==========================
 * Data Binding is an Extension from View Binding, where you can connect XML-Layouts with Data e.g
 * via ViewModel-Properties or simply variables.
 *
 * Example:
 * <layout xmlns:android="http://schemas.android.com/apk/res/android">
 *     <data>
 *         <variable
 *             name="username"
 *             type="String" />
 *     </data>
 *
 *     <TextView
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:text="@{username}" />
 * </layout>
 *
 * Setup:
 *  1. build.gradle.kts -> activate dataBinding in buildFeatures
 *  2. Implement it in a layout file (see above)
 *  3. In the activity you can access now variables:
 *      val binding = ActivityMainBinding.inflate(layoutInflater)
 *      binding.username = "Dennis Data-Binding"
 *
 * Differences:
 * +----------------------------------------------------------------+
 * | Comparison            | View Binding | Data Binding            |
 * +----------------------------------------------------------------+
 * | Automatic View-Access | Yes          | Yes                     |
 * | Binding Data          | No           | Yes                     |
 * | Expression Support    | No           | Yes (e.g @user.name)    |
 * | Complexity            | Low          | Higher but mor powerful |
 * | Performance           | Faster       | Slower (Expressions)    |
 * +----------------------------------------------------------------+
 *
 * When which to use?
 * View Binding:
 *  Easy Access to Views
 *  Fast and uncomplicated work
 * Data Binding:
 *  No Logical Code in Activity
 *  Dynamically adjustable UI by Data
 *
 * Researched using ChatGPT 4o on 26.03.2025 17:00
 */