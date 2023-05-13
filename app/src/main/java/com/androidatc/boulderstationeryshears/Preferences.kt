/*
This activity is started if the user presses the 'Preferences' button on the main activity. This activity
will allow a user to change a couple settings while using the application. They can add or delete
usernames that are used to show who's turn it is. The user can also decide between text or image buttons
to be used during the game. The application will use image buttons as the default setting. A home button
will allow the user to return to the main activity with their settings saved.
 */

package com.androidatc.boulderstationeryshears

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import com.androidatc.boulderstationeryshears.databinding.ActivityPreferencesBinding

class Preferences : AppCompatActivity() {

    // declare view binding variable for UI
    private lateinit var binding: ActivityPreferencesBinding
    // declare variable to reference stored data
    private lateinit var sharedPreferences: SharedPreferences
    // adapter will access array to store user names
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // reference stored data in shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // stores whether the application will use image or text buttons.
        val useImages = sharedPreferences.getBoolean("useImages", true)

        // radio buttons will reflect current settings, with images being the default.
        if (useImages) {
            binding.useImages.isChecked = true
            binding.useText.isChecked = false
        } else {
            binding.useImages.isChecked = false
            binding.useText.isChecked = true
        }

        // Set listener for radio buttons
        binding.useImages.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Save new setting to shared preferences
                sharedPreferences.edit().putBoolean("useImages", true).apply()
            }
        }

        binding.useText.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Save new setting to shared preferences
                sharedPreferences.edit().putBoolean("useImages", false).apply()
            }
        }

        // get previously created usernames
        val allUserNames = getUserNames().toMutableList()

        // adapter controls drop down spinner
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allUserNames)

        // save user button stores user entered name to be used later
        binding.saveUserBtn.setOnClickListener {
            val userName = binding.newUserEditText.text.toString()
            if (userName.isNotBlank()) {
                addUser(userName)
                allUserNames.add(userName)
                adapter.notifyDataSetChanged()
                binding.newUserEditText.setText("")
            }
        }

        // delete all users button removes all saved users from stored data
        binding.deleteUsersBtn.setOnClickListener {
            adapter.clear()
            clearUserNames()
        }

        // Home button returns user home with set preferences
        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }

    // function used to add users to shared preferences
    private fun addUser(userName: String) {
        val userSet = getUserSet().toMutableSet()
        userSet.add(userName)
        sharedPreferences.edit().putStringSet("userNames", userSet).apply()
    }

    private fun getUserNames(): List<String> {
        val userSet = getUserSet()
        return userSet.toList()
    }

    private fun getUserSet(): Set<String> {
        return sharedPreferences.getStringSet("userNames", setOf())!!
    }

    // function will clear all user names from list
    private fun clearUserNames() {
        sharedPreferences.edit().remove("userNames").apply()
    }
}