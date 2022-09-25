package com.uwdp.whatyouwant


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.uwdp.whatyouwant.databinding.FragmentThreeBinding

class ThreeFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        return return FragmentThreeBinding.inflate(inflater, container, false).root
    }
}