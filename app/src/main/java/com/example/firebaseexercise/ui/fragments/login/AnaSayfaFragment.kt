package com.example.firebaseexercise.ui.fragments.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.firebaseexercise.R
import com.example.firebaseexercise.databinding.FragmentAnaSayfaBinding
import com.example.firebaseexercise.ui.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnaSayfaFragment : Fragment() {


    private lateinit var binding: FragmentAnaSayfaBinding
    val viewModel: UserViewModel by viewModels()

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            account?.let {
                viewModel.firebaseAuthWithGoogle(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnaSayfaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.googleSignInButton.setOnClickListener {
            val signInClient =viewModel.getGoogleSignInClient(requireActivity())
            googleSignInLauncher.launch(signInClient.signInIntent)
        }

        binding.btnLogin.setOnClickListener {
            val action = AnaSayfaFragmentDirections.actionAnaSayfaFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        binding.btnRegister.setOnClickListener {
            val action = AnaSayfaFragmentDirections.actionAnaSayfaFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })

        // AnaSayfaFragment'de
        // AnaSayfaFragment'de
        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate) {
                val action = AnaSayfaFragmentDirections.actionAnaSayfaFragmentToPersonFragment()
                findNavController().navigate(action)
                viewModel.setNavigateToHome(false)  // Yönlendirmeyi gerçekleştirdikten sonra false yapıyoruz
            }
        })

    }
    private fun showExitDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Çıkış Yap")
            .setMessage("Uygulamadan çıkmak istediğinize emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                requireActivity().finish() // Uygulamayı kapat
            }
            .setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss() // Diyaloğu kapat
            }
            .show()

    }

}