/*
This activity is called if image buttons is enabled. This activity is where player one will select
their move. The app will move to the next activity after the player clicks one of the buttons. The next
activity will either be the third phase if player two is a human, or it will move straight to the
results activity if player 2 is a computer.
*/

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidatc.boulderstationeryshears.databinding.ActivitySecondPhaseBinding
import java.util.*

class SecondPhase : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivitySecondPhaseBinding
    // store player moves
    private var player1Move: String = ""
    private var player2Move: String = ""
    // store if opponent is human or not
    private var humanOpponent: Boolean = false
    // reference persistent data
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPhaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create instance of shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // determine if human opponent with intent passed from previous activity
        humanOpponent = intent.getBooleanExtra("humanOpponent", false)

        // get the current user name from shared preferences
        val currentPlayer1Name = sharedPreferences.getString("player1UserName", "") ?: ""

        // update the text view with the current user name
        binding.player1TextView.text = currentPlayer1Name

        // Whichever of these three buttons is pressed first will determine player 1 move and start next activity
        binding.boulderBtn.setOnClickListener {
            player1Move = "boulder"
            determineOpponent()
        }
        binding.stationeryBtn.setOnClickListener {
            player1Move = "stationery"
            determineOpponent()
        }
        binding.shearsBtn.setOnClickListener {
            player1Move = "shears"
            determineOpponent()
        }


    }

    // Determines if player 2 is a computer or human, then will move to appropriate activity
    fun determineOpponent() {
        // if opponent is a player, moves to thirdActivity for player 2 to play their move
        if (humanOpponent) {
            val intent = Intent(this, ThirdPhase::class.java)
            intent.putExtra("player1Move", player1Move)
            startActivity(intent)
        }
        // if opponent is a computer, calls function to determine a random move for player 2
        else {
            player2Move = computerMove()
            val intent = Intent(this, Results::class.java)
            intent.putExtra("player1Move", player1Move)
            intent.putExtra("player2Move", player2Move)
            intent.putExtra("humanOpponent", humanOpponent)
            startActivity(intent)
        }
    }

    // Function determines opponent move if they are a computer
    fun computerMove(): String {
        // list holds potential moves for computer
        val moves = listOf("boulder", "stationery", "shears")
        val random = Random()
        val randomIndex = random.nextInt(moves.size)
        // return randomized string value to determine move
        return moves[randomIndex]
    }
}