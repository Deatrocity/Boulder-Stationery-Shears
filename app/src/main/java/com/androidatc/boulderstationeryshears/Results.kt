/*
This activity will be shown after both players have selected their moves. It will determine who won
the round based on the player moves which are passed along from previous activities using intents, then
it will display who won using text views in the UI. There will be a rematch button allowing the user
to start another match retaining all settings from previous game, or a home button to return to the
main menu.
 */

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidatc.boulderstationeryshears.databinding.ActivityResultsBinding

class Results : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivityResultsBinding
    // shared preference variable for accessing data
    private lateinit var sharedPreferences: SharedPreferences
    private var humanOpponent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // access app-wide data stored in shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // references whether text or image buttons are selected to determine rematch settings
        val useImages = sharedPreferences.getBoolean("useImages", true) // Default value is true

        // receive player moves from previous activity using intents
        val player1Move = intent.getStringExtra("player1Move")
        val player2Move = intent.getStringExtra("player2Move")

        // receive humanopponent boolean for rematch purposes.
        humanOpponent = intent.getBooleanExtra("humanOpponent", false)

        // booleans will determine who wins, loses, or if it is a draw
        var player1Win = false
        var player2Win = false
        var player1Draw = false
        var player2Draw = false

        // get the current user names from shared preferences
        val currentPlayer1 = sharedPreferences.getString("player1UserName", "") ?: ""
        val currentPlayer2 = sharedPreferences.getString("player2UserName", "") ?: ""

        // update the Text views with the current usernames
        binding.user1Name.text = currentPlayer1
        binding.user2Name.text = currentPlayer2


        // These if statements will consider the player moves and decide a winner. The text view will be updated.
        if (player1Move == "boulder" && player2Move == "boulder") {
            player1Draw = true
            player2Draw = true
            binding.whoWon.text = "It's a Draw!"
        }
        if (player1Move == "boulder" && player2Move == "stationery") {
            player2Win = true
            binding.whoWon.text = "Player 2 Wins!"
            binding.explanation.text = "Stationery beats boulder"
        }
        if (player1Move == "boulder" && player2Move == "shears") {
            player1Win = true
            binding.whoWon.text = "Player 1 Wins!"
            binding.explanation.text = "Boulder beats shears"
        }
        if (player1Move == "stationery" && player2Move == "stationery") {
            player1Draw = true
            player2Draw = true
            binding.whoWon.text = "It's a Draw!"
        }
        if (player1Move == "stationery" && player2Move == "boulder") {
            player1Win = true
            binding.whoWon.text = "Player 1 Wins!"
            binding.explanation.text = "Stationery beats boulder"
        }
        if (player1Move == "stationery" && player2Move == "shears") {
            player2Win = true
            binding.whoWon.text = "Player 2 Wins!"
            binding.explanation.text = "Shears beats stationery"
        }
        if (player1Move == "shears" && player2Move == "shears") {
            player1Draw = true
            player2Draw = true
            binding.whoWon.text = "It's a Draw!"
        }
        if (player1Move == "shears" && player2Move == "boulder") {
            player2Win = true
            binding.whoWon.text = "Player 2 Wins!"
            binding.explanation.text = "Boulder beats shears"
        }
        if (player1Move == "shears" && player2Move == "stationery") {
            player1Win = true
            binding.whoWon.text = "Player 1 Wins!"
            binding.explanation.text = "Shears beats stationery"
        }

        // This will set the imageView for player 1 based on their move
        if (player1Move == "boulder") {
            binding.player1Choice.setImageResource(R.drawable.boulder)
        } else if (player1Move == "stationery") {
            binding.player1Choice.setImageResource(R.drawable.stationery)
        } else if (player1Move == "shears") {
            binding.player1Choice.setImageResource(R.drawable.shears)
        }

        // This will set the imageView for player 2 based on their move
        if (player2Move == "boulder") {
            binding.player2Choice.setImageResource(R.drawable.boulder)
        } else if (player2Move == "stationery") {
            binding.player2Choice.setImageResource(R.drawable.stationery)
        } else if (player2Move == "shears") {
            binding.player2Choice.setImageResource(R.drawable.shears)
        }

        // rematch button restarts game with pc/human settings
        binding.rematchButton.setOnClickListener {
            if (useImages) {
                val intent = Intent(this, SecondPhase::class.java)
                intent.putExtra("humanOpponent", humanOpponent)
                startActivity(intent)
            } else {
                val intent = Intent(this, SecondPhaseAlt::class.java)
                intent.putExtra("humanOpponent", humanOpponent)
                startActivity(intent)
            }
        }

        // home button will take the user home.
        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}