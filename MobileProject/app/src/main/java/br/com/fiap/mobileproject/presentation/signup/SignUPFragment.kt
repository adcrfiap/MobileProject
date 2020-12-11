package br.com.fiap.mobileproject.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.concrete.canarinho.validator.Validador
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mobileproject.data.repository.UserRepositoryImpl
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.CreateUserUseCase
import br.com.fiap.mobileproject.presentation.base.BaseFragment
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignUPFragment : BaseFragment() {

    private lateinit var etUserNameSignUp: EditText
    private lateinit var etEmailSignUp: EditText
    private lateinit var etCNPJSignUp: EditText
    private lateinit var etPhoneSignUp: EditText
    private lateinit var etPasswordSignUp: EditText
    private lateinit var cbTermsSignUp: LottieAnimationView
    private lateinit var btCreateAccount: Button
    private lateinit var btBack: Button


    private var checkBoxDone = false

    override val layout = R.layout.fragment_sign_u_p
    private val signUPViewModel: SignUPViewModel by lazy {
        ViewModelProvider(
            this,
            SignUPViewModelFactory(
                CreateUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(SignUPViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()


    }

    private fun setUpView(view: View) {
        etUserNameSignUp = view.findViewById(R.id.etNameSignUp)
        etEmailSignUp = view.findViewById(R.id.etEmailSignUp)
        etCNPJSignUp = view.findViewById(R.id.etCNPJSignUp)
        etPhoneSignUp = view.findViewById(R.id.etPhoneSignUp)
        etPasswordSignUp = view.findViewById(R.id.etPasswordSignUP)
        btCreateAccount = view.findViewById(R.id.btCreateAccount)
        btBack = view.findViewById(R.id.btBackSignup)
        setUpListener()
    }

    private fun setUpListener() {
        etPhoneSignUp.addTextChangedListener(TelefoneTextWatcher(object : EventoDeValidacao {
            override fun totalmenteValido(valorAtual: String?) {}
            override fun invalido(valorAtual: String?, mensagem: String?) {}
            override fun parcialmenteValido(valorAtual: String?) {}
        }))

        etCNPJSignUp.addTextChangedListener(
            MascaraNumericaTextWatcher.Builder()
            .paraMascara("##.###.###/####-##")
            .comValidador(Validador.CNPJ)
            .build() );

        btCreateAccount.setOnClickListener {
            signUPViewModel.create(
                etUserNameSignUp.text.toString(),
                etEmailSignUp.text.toString(),
                etCNPJSignUp.text.toString(),
                etPhoneSignUp.text.toString(),
                etPasswordSignUp.text.toString()
            )
        }

        btBack.setOnClickListener {
            findNavController().navigate(R.id.login_nav_graph)
        }

//        setUpCheckboxListener()
    }

//    private fun setUpCheckboxListener() {
//        cbTermsSignUp.setOnClickListener {
//            if (checkBoxDone) {
//                cbTermsSignUp.speed = -1f
//                cbTermsSignUp.playAnimation()
//                checkBoxDone = false
//            } else {
//                cbTermsSignUp.speed = 1f
//                cbTermsSignUp.playAnimation()
//                checkBoxDone = true
//            }
//        }
//    }

    private fun registerObserver() {
        this.signUPViewModel.newUserState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.login_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Authenticating...")
            }
        })
    }
}