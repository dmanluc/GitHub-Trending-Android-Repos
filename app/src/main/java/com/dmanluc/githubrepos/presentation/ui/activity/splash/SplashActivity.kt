package com.dmanluc.githubrepos.presentation.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos.TrendingReposActivity

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
        val intent = Intent(this, TrendingReposActivity::class.java)
        startActivity(intent)
        finish()
    }

}