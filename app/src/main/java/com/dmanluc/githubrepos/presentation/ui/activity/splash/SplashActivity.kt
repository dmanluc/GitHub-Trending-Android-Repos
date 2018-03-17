package com.dmanluc.githubrepos.presentation.ui.activity.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

/**
 * Initial splash activity
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val intent = Intent(this, CharactersActivity::class.java)
        //startActivity(intent)
        Handler().postDelayed({ finish() }, 3000)
    }

}