package com.example.chatapp.ui.addRoom

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.base.BaseActivity
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.other.SpinnerCategoryAdapter

class AddRoomActivity : BaseActivity<AddRoomViewModel, ActivityAddRoomBinding>(), Navigator {
    private lateinit var spinnerAdapter: SpinnerCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.navigator = this
        spinnerAdapter = SpinnerCategoryAdapter(viewModel.spinnerCategoryList)
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, index: Int, id: Long) {
                viewModel.selectedItem = viewModel.spinnerCategoryList[index]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_room
    }

    override fun initViewModel(): AddRoomViewModel {
        return ViewModelProvider(this)[AddRoomViewModel::class.java]
    }
}