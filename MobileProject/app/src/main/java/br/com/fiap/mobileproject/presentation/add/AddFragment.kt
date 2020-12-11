package br.com.fiap.mobileproject.presentation.add

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.data.remote.datasource.ProductRemoteFirebaseDataSourceImpl
import br.com.fiap.mobileproject.data.repository.ProductRepositoryImpl
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.CreateProductsUseCase
import br.com.fiap.mobileproject.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AddFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_add
    private lateinit var btBackAdd: Button
    private lateinit var btCreate: Button
    private lateinit var etDescription: EditText
    private lateinit var etValue: EditText
    private lateinit var etQuantity: EditText

    private val addViewModel: AddViewModel by lazy {
        ViewModelProvider(
                this,
                AddViewModelFactory(
                        CreateProductsUseCase(
                                ProductRepositoryImpl(
                                        ProductRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        )
                )
        ).get(AddViewModel::class.java)
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        registerBackPressedAction()
    }

    private fun setUpView(view: View) {
        btBackAdd = view.findViewById(R.id.btBackAdd)
        btCreate = view.findViewById(R.id.btCreate)
        etDescription = view.findViewById(R.id.etDescription)
        etQuantity = view.findViewById(R.id.etQuantity)
        etValue = view.findViewById(R.id.etValue)

        setUpListener()
        registerObserver()
    }

    private fun setUpListener() {

        btBackAdd.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }

        btCreate.setOnClickListener {
            addViewModel.create(etDescription.text.toString(),
                                etQuantity.text.toString().toDouble(),
                                etValue.text.toString().toDouble())
        }

    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.main_nav_graph)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun registerObserver() {
        this.addViewModel.createProductsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.main_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Creating...")
            }
        })
    }

}