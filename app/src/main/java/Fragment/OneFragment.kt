package com.uwdp.whatyouwant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.uwdp.whatyouwant.databinding.FragmentOneBinding

class OneFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return FragmentOneBinding.inflate(inflater, container, false).root
    }

}