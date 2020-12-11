package br.com.fiap.mobileproject.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mobileproject.data.repository.UserRepositoryImpl
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.exceptions.UserNotLoggedException
import br.com.fiap.mobileproject.domain.usecases.LoginUseCase
import br.com.fiap.mobileproject.domain.usecases.ResetPasswordUseCase
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase
import br.com.fiap.mobileproject.presentation.base.auth.BaseAuthFragment
import br.com.fiap.mobileproject.presentation.base.auth.NAVIGATION_KEY
import br.com.fiap.mobileproject.presentation.login.LoginViewModel
import br.com.fiap.mobileproject.presentation.login.LoginViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private lateinit var btSignOut: Button
    private lateinit var ibList: ImageButton
    private lateinit var ibAdd: ImageButton

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                SignOutUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(HomeViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
        registerBackPressedAction()
    }

    private fun setUpView(view: View) {
        btSignOut = view.findViewById(R.id.btsignout)
        ibList    = view.findViewById(R.id.iblist)
        ibAdd    = view.findViewById(R.id.ibadd)


        btSignOut.setOnClickListener {
            homeViewModel.doSignOut( )
        }

        ibList.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }

        ibAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun registerObserver() {
        homeViewModel.signOutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
            }
        })


    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun showSuccess() {
        hideLoading()
         findNavController().navigate(R.id.main_nav_graph)
    }


    private fun showError(throwable: Throwable) {
        hideLoading()
        showMessage(throwable.message)
    }

}