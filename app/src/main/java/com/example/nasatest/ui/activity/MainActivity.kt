package com.example.nasatest.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasatest.R
import com.example.nasatest.data.remote.Webservices
import com.example.nasatest.data.repository.NasaRepositoryImpl
import com.example.nasatest.ui.adepter.ItemAdepter
import com.example.nasatest.viewmodel.MainViewModel
import com.example.nasatest.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var itemAdepter: ItemAdepter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()

        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(NasaRepositoryImpl(Webservices.instance))
        )
            .get(MainViewModel::class.java)
        viewModel.items.observe(this, Observer {
            itemAdepter.items.clear()
            itemAdepter.items.addAll(it)
            itemAdepter.notifyDataSetChanged()
        })



        viewModel.errorMessage.observe(this, Observer {
            tvMessage.text = it
        })
        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })

        viewModel.fetchItems()

        btnRetry.setOnClickListener {
            viewModel.fetchItems()
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.fetchItems()
        }

    }

    private fun displayProgressbar() {
        if (!swipeRefresh.isRefreshing) {
            progressbar.visibility = View.VISIBLE
            rvAlbum.visibility = View.GONE
            llMessageContainer.visibility = View.GONE
        }

    }

    private fun displayMessageContainer() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
        llMessageContainer.visibility = View.VISIBLE
        rvAlbum.visibility = View.GONE
        progressbar.visibility = View.GONE
    }

    private fun displayList() {
        if (swipeRefresh.isRefreshing) {
            Toast.makeText(this, R.string.data_is_refreshed, Toast.LENGTH_LONG).show()
            swipeRefresh.isRefreshing = false
        }
        llMessageContainer.visibility = View.GONE
        rvAlbum.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
    }

    private fun setUpRecyclerView() {
        rvAlbum.layoutManager = GridLayoutManager(this, 2)
        itemAdepter = ItemAdepter(mutableListOf())
        rvAlbum.adapter = itemAdepter
    }
}
