package com.submission.dicodingstory.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.submission.dicodingstory.R
import com.submission.dicodingstory.data.factory.AuthPreferencesFactory
import com.submission.dicodingstory.data.preferences.AuthPreferences
import com.submission.dicodingstory.ui.fragment.RegisterFragment
import com.submission.dicodingstory.ui.fragment.TimelineFragment
import com.submission.dicodingstory.utils.Constants.Companion.dataStore
import com.submission.dicodingstory.viewmodel.AuthPreferencesViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = AuthPreferences.getInstance(dataStore)
        val authViewModel = ViewModelProvider(
            this,
            AuthPreferencesFactory(pref)
        )[AuthPreferencesViewModel::class.java]

        authViewModel.getUserData().observe(this) {
            if (it != null && it.token != "" && it.name != "") {
                Log.d("Token", it.token!!)
                Log.d("Name", it.name!!)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, TimelineFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, RegisterFragment())
                    .commit()
            }
        }


    }
}