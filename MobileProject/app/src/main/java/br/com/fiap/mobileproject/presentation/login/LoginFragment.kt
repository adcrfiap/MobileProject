package br.com.fiap.mobileproject.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mobileproject.data.repository.UserRepositoryImpl
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.exceptions.UserNotLoggedException
import br.com.fiap.mobileproject.domain.usecases.LoginUseCase
import br.com.fiap.mobileproject.domain.usecases.ResetPasswordUseCase
import br.com.fiap.mobileproject.presentation.base.BaseFragment
import br.com.fiap.mobileproject.presentation.base.auth.NAVIGATION_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : BaseFragment()  {


    override val layout = R.layout.fragment_login

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(
                LoginUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                        FirebaseAuth.getInstance(),
                        FirebaseFirestore.getInstance()
                    )
                    )
                ),
                ResetPasswordUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(LoginViewModel::class.java)
    }


    private lateinit var btLogin: Button
    private lateinit var btReset: Button
    private lateinit var btSignIn: Button
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var tvAbout: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
        registerBackPressedAction()
    }

    private fun setUpView(view: View) {
        btLogin = view.findViewById(R.id.btLogin)
        btReset = view.findViewById(R.id.btReset)
        btSignIn = view.findViewById(R.id.btSignUp)
        etEmailLogin = view.findViewById(R.id.etEmail)
        etPasswordLogin = view.findViewById(R.id.etPassword)
        tvAbout         = view.findViewById(R.id.tvAbout)

        btLogin.setOnClickListener {
            loginViewModel.doLogin(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            )
        }

        btReset.setOnClickListener {
            loginViewModel.resetPassword(etEmailLogin.text.toString())
        }

        tvAbout.setOnClickListener{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment_to_aboutFragment)
        }

        btSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUPFragment)
        }
    }

    private fun registerObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> showLoading("Authenticating")
            }
        })

        loginViewModel .resetPasswordState .observe( viewLifecycleOwner,
                Observer {
                    when (it) {
                        is RequestState .Success -> {
                            hideLoading()
                            showMessage( it.data)
                        }
                        is RequestState .Error -> showError( it.throwable )
                        is RequestState .Loading -> showLoading( "Resend Password reset email" )
                    }
                })
    }


    private fun showSuccess() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdForArguments == null) {
            findNavController().navigate(R.id.main_nav_graph)
        } else {
            findNavController().popBackStack(navIdForArguments, false)
        }
    }


    private fun showError(throwable: Throwable) {
        hideLoading()

        if(throwable is UserNotLoggedException){

        }else {

            etEmailLogin.error = null
            etPasswordLogin.error = null

            showMessage(throwable.message)
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}