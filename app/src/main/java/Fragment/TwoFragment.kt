package com.uwdp.whatyouwant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uwdp.whatyouwant.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return FragmentTwoBinding.inflate(inflater, container, false).root
    }

}