package com.example.new_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.new_app.R
import com.example.new_app.databinding.ActivityMainBinding
import com.example.new_app.ui.category.CategoryFragment
import com.example.new_app.ui.categoryDetils.CategoryDetilsFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)


                supportFragmentManager.
                beginTransaction()
                    .replace(R.id.frameLayout, CategoryFragment())
                    .commit()

    }
}