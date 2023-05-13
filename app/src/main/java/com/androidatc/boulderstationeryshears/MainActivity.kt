/*
Boulder Stationery Shears
version 1.0
13MAY2023

This application will allow a user to play a 'rock paper scissors' style game with either a human or
computer opponent. It uses shared preferences to store persistent data such user names or using either
image or text style buttons depending on settings made in the preferences activity. There is also a help
activity that is read-only that has some helpful information about how the application works.
 */

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.androidatc.boulderstationeryshears.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivityMainBinding
    // declare variable to reference stored data
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    // for passing player 1 name to second activity with intent
    private var player1Selected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // access stored data in shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // get all previously created usernames
        val allUserNames = getUserNames()

        // adapter controls spinner widgets
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allUserNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // bind spinners to adapters
        binding.playerOneSpinner.adapter = adapter
        binding.playerTwoSpinner.adapter = adapter

        // get the current user name from shared preferences
        val player1UserName = sharedPreferences.getString("player1UserName", "") ?: ""
        val player2UserName = sharedPreferences.getString("player2UserName", "") ?: ""

        // spinner allows player one to select their user name from shared preferences
        binding.playerOneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // get the selected user name
                player1Selected = allUserNames[position]

                // save the selected user name to shared preferences
                sharedPreferences.edit().putString("player1UserName", player1Selected).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        // spinner allows player two to select their user name from shared preferences
        binding.playerTwoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // get the selected user name
                val player2Selected = allUserNames[position]

                // save the selected user name to shared preferences
                sharedPreferences.edit().putString("player2UserName", player2Selected).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }


        // Play button starts firstPhase, beginning new game, passes data with intents
        binding.playBtn.setOnClickListener {
            val intent = Intent(this, FirstPhase::class.java)
            intent.putExtra("player1Selected", player1Selected)
            startActivity(intent)
        }

        // preferences button starts preference activity
        binding.preferenceBtn.setOnClickListener {
            val intent = Intent(this, Preferences::class.java)
            startActivity(intent)
        }

        // help button starts read-only help activity
        binding.helpBtn.setOnClickListener {
            val intent = Intent(this, Help::class.java)
            startActivity(intent)
        }

    }

    // populate spinner with usernames stored in shared preferences
    private fun getUserNames(): List<String> {
        val userSet = sharedPreferences.getStringSet("userNames", setOf())!!
        return userSet.toList()
    }
}