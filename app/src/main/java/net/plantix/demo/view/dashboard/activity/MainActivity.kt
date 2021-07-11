package net.plantix.demo.view.dashboard.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import net.plantix.demo.R
import net.plantix.demo.data.Repository.RepositoryUsername
import net.plantix.demo.data.model.ModelUserName
import net.plantix.demo.data.retrofit.ApiHelper
import net.plantix.demo.data.retrofit.RetrofitClient
import net.plantix.demo.util.Status
import net.plantix.demo.util.extensions
import net.plantix.demo.view.dashboard.adapter.UserNameAdapter
import net.plantix.demo.viewmodel.VMusername


class MainActivity : AppCompatActivity() {
    private lateinit var recylcerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var userAdapter: UserNameAdapter
    private lateinit var userViewModel: VMusername
    private var isOnline: Boolean = false
    private lateinit var layParent: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layParent = findViewById(R.id.parentLayout)
        setupViewModel()
        initRecyclerView()
        setupObservers()
    }

    private fun setupViewModel() {
        isOnline = extensions.isOnline(this@MainActivity)
        userViewModel = ViewModelProviders.of(this).get(VMusername::class.java)
    }

    fun initRecyclerView(){
        recylcerView = findViewById(R.id.rv_main)
        progressBar = findViewById(R.id.pb_loading)
        userAdapter = UserNameAdapter(this, ArrayList())
        recylcerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun setupObservers() {
        userViewModel.setRepository(
            RepositoryUsername(
                ApiHelper(
                    RetrofitClient.apiInterface,
                    0,
                    20
                )
            )
        )
        userViewModel.getUserNames(isOnline)
        lifecycle.coroutineScope.launchWhenCreated {
            userViewModel.stateFlow.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        recylcerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        it.data?.let { users ->
                            retrieveList(users as ArrayList<ModelUserName>)
                        }
                    }
                    Status.ERROR -> {
                        recylcerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Snackbar.make(
                            layParent,
                            it.message.toString().ifBlank { "Not able to connect with Server" },
                            5000
                        ).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recylcerView.visibility = View.GONE
                    }
                }
            }
        }
    }
    fun retrieveList(users: ArrayList<ModelUserName>) {
        userAdapter.SetData(users)
        userAdapter.notifyDataSetChanged()
    }
}