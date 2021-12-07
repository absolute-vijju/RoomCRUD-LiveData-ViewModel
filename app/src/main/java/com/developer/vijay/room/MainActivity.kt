package com.developer.vijay.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.developer.vijay.room.roomcrud.R
import com.developer.vijay.room.roomcrud.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val navController = findNavController(R.id.navHostFragment)
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
////            mBinding.header.tvTitle.text = destination.label
        }
        mBinding.bottomNavigation.setupWithNavController(navController)
    }
}