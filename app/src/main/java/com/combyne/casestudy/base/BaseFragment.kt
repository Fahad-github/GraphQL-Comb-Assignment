package com.combyne.casestudy.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    private var ctx: FragmentActivity? = null

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.ctx = activity
    }

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)
}