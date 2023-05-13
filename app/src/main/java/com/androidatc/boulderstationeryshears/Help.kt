/*
This is a read-only activity that is started if the user presses 'help' button in main activity.
It contains some helpful information including how the game is played and some settings that the user
can change in the preference window.
 */

package com.androidatc.boulderstationeryshears

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidatc.boulderstationeryshears.databinding.ActivityHelpBinding

class Help : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the text for first paragraph
        binding.helpParagraph1.text = "Boulder Stationery Shears is the mobile solution to any unresolvable dispute " +
                ". Based on the classic game: Rock Paper Scissors, Boulder Stationery Shears will offer you an interactive " +
                "version of the beloved game. Whether playing against a friend or the sophisticated AI opponent, you can always take hard to make" +
                " decisions to the app!"

        // set text for second paragraph
        binding.helpParagraph2.text = "Remember: Boulder beats shears, shears beats stationery, and stationery beats boulder."

        // set text for third paragraph
        binding.helpParagraph3.text = "In preferences you may add and delete user names to be used during the game for both players. You may also" +
                " choose between using buttons with images on them or with text during player turns depending on personal preference."

    }


}