/*
This activity is called after the user presses play button in main activity. It allows player one
to decide if player two is controlled by a human or the computer. If it is a human, it will allow
a second player to decide player 2's move, if not, a random move will be selected for player 2.
 */

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidatc.boulderstationeryshears.databinding.ActivityFirstPhaseBinding

class FirstPhase : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivityFirstPhaseBinding
    // declare string variable to store first players username
    private var player1Name: String = ""
    // variable references shared preference instance
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPhaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // reference shared preferences to access persistent data
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Get boolean value from shared preferences that will decide if image or text buttons are used.
        val useImages = sharedPreferences.getBoolean("useImages", true) // Default value is true
        // boolean variable will store if player 2 is human or computer
        var humanOpponent = false

        // customize text view with user selected username
        binding.player1Holder.text = intent.getStringExtra("player1Selected")

        // check radio button values to determine if opponent is human or computer
        binding.playerSelectionGroup.setOnCheckedChangeListener { group, id ->
            if (id == binding.radioHuman.id) {
                humanOpponent = true
            } else if (id == binding.radioComputer.id) {
                humanOpponent = false
            }
        }
        // set default value to human opponent
        binding.radioHuman.isChecked = true

        // Select button starts starts next activity and passes along if opponent is human or computer
        binding.opponentSelectBtn.setOnClickListener {
            if (useImages) {
                val intent = Intent(this, SecondPhase::class.java)
                intent.putExtra("humanOpponent", humanOpponent)
                startActivity(intent)
                // Use image buttons
            } else {
                // Use text buttons
                val intent = Intent(this, SecondPhaseAlt::class.java)
                intent.putExtra("humanOpponent", humanOpponent)
                startActivity(intent)
            }
        }
    }
}