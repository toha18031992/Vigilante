package com.crazylegend.vigilante.deviceinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels

import com.crazylegend.viewbinding.viewBinding
import com.crazylegend.vigilante.R
import com.crazylegend.vigilante.abstracts.AbstractFragment
import com.crazylegend.vigilante.databinding.LayoutRecyclerBinding
import com.crazylegend.vigilante.di.providers.AdapterProvider
import com.crazylegend.vigilante.utils.onStartedRepeatingAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by crazy on 11/3/20 to long live and prosper !
 */
@AndroidEntryPoint
class DeviceInfoFragment : AbstractFragment<LayoutRecyclerBinding>(R.layout.layout_recycler) {

    @Inject
    lateinit var adapterProvider: AdapterProvider

    override val binding: LayoutRecyclerBinding by viewBinding(LayoutRecyclerBinding::bind)

    private val adapter by lazy {
        adapterProvider.deviceInfoAdapter
    }

    private val deviceInfoVM by viewModels<DeviceInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter

        onStartedRepeatingAction {
            deviceInfoVM.deviceInfoList.collect {
                adapter.submitList(it)
            }
        }
    }
}