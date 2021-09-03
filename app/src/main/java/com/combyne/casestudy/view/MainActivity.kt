package com.combyne.casestudy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.combyne.casestudy.R
import com.combyne.casestudy.base.BaseActivity
import com.combyne.casestudy.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this


    }
}