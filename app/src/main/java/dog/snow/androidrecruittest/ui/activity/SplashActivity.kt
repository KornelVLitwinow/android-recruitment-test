package dog.snow.androidrecruittest.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.SplashActivityBinding
import dog.snow.androidrecruittest.extension.transitionCompleted
import dog.snow.androidrecruittest.viewmodel.SplashViewModel
import dog.snow.androidrecruittest.viewmodel.SplashViewModel.ViewStatus.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var _binding: SplashActivityBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        _binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.observeViewState.observe(this) {
            when (it) {
                Success -> openHomeActivitySuccess()
                Error -> openHomeActivityError()
                NetworkErrorConnection -> showError()
            }
        }
    }

    private fun openHomeActivitySuccess() {
        binding.viewMotion.transitionToStart()
        binding.viewMotion.transitionCompleted {
            finish()
            startActivity(HomeActivity.openActivity(this@SplashActivity))
        }
    }

    private fun openHomeActivityError() {
        binding.viewMotion.transitionToStart()
        finish()
        startActivity(HomeActivity.openActivity(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showError() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> viewModel.loadData() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    companion object {
        fun openActivity(context: Context) = Intent(context, SplashActivity::class.java)
    }
}