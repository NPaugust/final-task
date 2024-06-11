package com.avgust.final_task.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.avgust.final_task.MainActivity
import com.avgust.final_task.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : Fragment(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        launch {

            (activity as MainActivity).hideBottomNavigation()
            super.onDetach()

            delay(2000)
            withContext(Dispatchers.Main){
                if(onBoardingFinished()){
                    findNavController().navigate(R.id.action_splashFragment_to_authPagerFragment)
                }else{                                //
                    findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
            }
        }

    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("ONBOARD", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("FINISHED", false)
    }



}