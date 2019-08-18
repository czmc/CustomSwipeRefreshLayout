package com.github.czmc.demo

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {
    private var mData = Array(100) {
        "item ${(Math.random() * 1000).toInt()}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview.adapter = ArrayAdapter(this, android.R.layout.test_list_item, android.R.id.text1, mData)
        val viewmodel = ViewModelProviders.of(this).get(DataViewModel::class.java)

        viewmodel.selected.observe(this, Observer {
            it?.copyInto(mData, 0, 0, it.size)
            (listview.adapter as ArrayAdapter<*>).notifyDataSetChanged()
        })

        refreshlayout.setOnRefreshListener {
            refreshlayout.isRefreshing = false
            viewmodel.selected.value = Array(100) {
                "item ${(Math.random() * 1000).toInt()}"
            }
        }

    }

    class DataViewModel : ViewModel() {
        val selected = MutableLiveData<Array<String>>()
    }

}