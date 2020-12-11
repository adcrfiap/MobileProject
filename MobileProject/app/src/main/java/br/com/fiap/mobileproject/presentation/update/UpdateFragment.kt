package br.com.fiap.mobileproject.presentation.update

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
import br.com.fiap.mobileproject.domain.entity.ProductUpdate
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.CreateProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.UpdateProductsUseCase
import br.com.fiap.mobileproject.presentation.add.AddViewModel
import br.com.fiap.mobileproject.presentation.add.AddViewModelFactory
import br.com.fiap.mobileproject.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class UpdateFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_update

    private lateinit var btBacUpd: Button
    private lateinit var btUpdate: Button
    private lateinit var etDescriptionUpd: EditText
    private lateinit var etValueUpd: EditText
    private lateinit var etQuantityUpd: EditText

    private val updateViewModel: UpdateViewModel by lazy {
        ViewModelProvider(
                this,
               UpdateViewModelFactory(
                        UpdateProductsUseCase(
                                ProductRepositoryImpl(
                                        ProductRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        )
                )
        ).get(UpdateViewModel::class.java)
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        registerBackPressedAction()
        registerObserver()
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.main_nav_graph)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setUpView(view: View) {
        btBacUpd = view.findViewById(R.id.btBackUpd)

        btUpdate = view.findViewById(R.id.btUpdate)
        etDescriptionUpd = view.findViewById(R.id.etDescriptionUpd)
        etQuantityUpd = view.findViewById(R.id.etQuantityUpd)
        etValueUpd = view.findViewById(R.id.etValueUpd)

        etDescriptionUpd.setText(ProductUpdate.description)
        etQuantityUpd.setText(ProductUpdate.quantity.toString())
        etValueUpd.setText(ProductUpdate.value.toString())

        btBacUpd.setOnClickListener {
            findNavController().navigate(R.id.listFragment)
        }

        btUpdate.setOnClickListener {
            ProductUpdate.description =  etDescriptionUpd.text.toString()
            ProductUpdate.quantity = etQuantityUpd.text.toString().toDouble()
            ProductUpdate.value    = etValueUpd.text.toString().toDouble()
            updateViewModel.update()
        }

    }

    private fun registerObserver() {
        this.updateViewModel.updateProductsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.listFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Updating...")
            }
        })
    }



}