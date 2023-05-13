/*
This activity will only be started if player 2 is a human opponent, and image buttons are enabled.
It is identical to the last activity but for player 2's turn instead of player 1. After a button is
pressed it will move to results activity to show who won the round.
*/

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidatc.boulderstationeryshears.databinding.ActivityThirdPhaseBinding

class ThirdPhase : AppCompatActivity() {

    // access UI with binding
    private lateinit var binding: ActivityThirdPhaseBinding
    // boolean is created to pass along to results activity for rematch purposes.
    private var humanOpponent: Boolean = true
    // variable stores player 2 move
    private var player2Move: String = ""
    // create instance of shared preferences to access persistent data
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdPhaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // human opponent is true if this activity is accessed. passed on to next activity with intent
        humanOpponent = true

        // get the current user name from shared preferences
        val currentPlayer2Name = sharedPreferences.getString("player2UserName", "") ?: ""

        // used to store player 1 move to pass along to next activity
        val player1Move = intent.getStringExtra("player1Move")

        // update the text view with the current user name
        binding.player2TextView.text = currentPlayer2Name

        // if player clicks boulder button, sets move to boulder
        binding.boulderBtn.setOnClickListener {
            player2Move = "boulder"
            val intent = Intent(this, Results::class.java)
            intent.putExtra("player2Move", player2Move)
            intent.putExtra("player1Move", player1Move)
            intent.putExtra("humanOpponent", humanOpponent)
            startActivity(intent)
        }

        // if player clicks stationery button, sets move to stationery
        binding.stationeryBtn.setOnClickListener {
            player2Move = "stationery"
            val intent = Intent(this, Results::class.java)
            intent.putExtra("player2Move", player2Move)
            intent.putExtra("player1Move", player1Move)
            intent.putExtra("humanOpponent", humanOpponent)
            startActivity(intent)
        }

        // if player clicks shears button, sets move to shears
        binding.shearsBtn.setOnClickListener {
            player2Move = "shears"
            val intent = Intent(this, Results::class.java)
            intent.putExtra("player2Move", player2Move)
            intent.putExtra("player1Move", player1Move)
            intent.putExtra("humanOpponent", humanOpponent)
            startActivity(intent)
        }


    }
}