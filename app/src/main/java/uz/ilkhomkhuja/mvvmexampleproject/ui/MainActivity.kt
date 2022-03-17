package uz.ilkhomkhuja.mvvmexampleproject.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.ilkhomkhuja.mvvmexampleproject.adapters.UserAdapter
import uz.ilkhomkhuja.mvvmexampleproject.database.db.AppDatabase
import uz.ilkhomkhuja.mvvmexampleproject.databinding.ActivityMainBinding
import uz.ilkhomkhuja.mvvmexampleproject.network.ApiClient
import uz.ilkhomkhuja.mvvmexampleproject.repositories.UserRepository
import uz.ilkhomkhuja.mvvmexampleproject.utils.NetworkHelper
import uz.ilkhomkhuja.mvvmexampleproject.utils.Status
import uz.ilkhomkhuja.mvvmexampleproject.viewmodels.UserViewModel
import uz.ilkhomkhuja.mvvmexampleproject.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var networkHelper: NetworkHelper
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        userRepository = UserRepository(ApiClient.apiService, AppDatabase.getInstance(this))
        networkHelper = NetworkHelper(this)
        initViewModel()
        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter

        userViewModel.getUsers().observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    userAdapter.submitList(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE

                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository, networkHelper)
        )[UserViewModel::class.java]
    }
}