package br.com.fiap.mobileproject.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.data.remote.datasource.ProductRemoteFirebaseDataSourceImpl
import br.com.fiap.mobileproject.data.repository.ProductRepositoryImpl
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.DeleteProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.FindProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.ListProductsUseCase
import br.com.fiap.mobileproject.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ListFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_list
    private lateinit var btBack: Button
    private lateinit var btSearch: Button
    private lateinit var etDescription: EditText

    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(
                this,
                ListViewModelFactory(
                        ListProductsUseCase(
                                ProductRepositoryImpl(
                                        ProductRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        ),
                        DeleteProductsUseCase(
                                ProductRepositoryImpl(
                                        ProductRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        ),
                        FindProductsUseCase(
                            ProductRepositoryImpl(
                                ProductRemoteFirebaseDataSourceImpl(
                                  FirebaseAuth.getInstance(),
                                  FirebaseFirestore.getInstance()
                            )
                        )
                    )
                )
        ).get(ListViewModel::class.java)
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListProductAdapter(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvlistproduct)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        setUpView(view)
        registerBackPressedAction()
        registerObserver(adapter)
        listViewModel.listProducts()
    }

    private fun setUpView(view: View) {

        btBack = view.findViewById(R.id.btBackUpd)
        btSearch = view.findViewById(R.id.btSearch)

        btBack.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }

        btSearch.setOnClickListener {
            etDescription = view.findViewById(R.id.etProductName)

            if (etDescription.text.toString().equals("")){
                listViewModel.listProducts()
            }else{
                listViewModel.findProduct(etDescription.text.toString())
            }

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

    private fun registerObserver(adapter: ListProductAdapter) {
        this.listViewModel.ListProductsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {

                    adapter.setProducts((it as RequestState.Success).data)
                    adapter.setModel(listViewModel)

                }
            }
        })

        this.listViewModel.FindProductsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {

                    adapter.setProducts((it as RequestState.Success).data)
                    adapter.setModel(listViewModel)

                }
            }
        })
    }

}