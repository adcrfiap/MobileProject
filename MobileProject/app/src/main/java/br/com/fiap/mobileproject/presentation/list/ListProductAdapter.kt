package br.com.fiap.mobileproject.presentation.list

import android.app.AlertDialog
import android.app.AlertDialog.*
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mobileproject.R
import br.com.fiap.mobileproject.domain.entity.Product
import br.com.fiap.mobileproject.domain.entity.ProductUpdate
import br.com.fiap.mobileproject.presentation.update.UpdateFragment

class ListProductAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<ListProductAdapter.ProdutoViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var produtos: MutableList<Product> = mutableListOf<Product>()
    private var listViewModel: ListViewModel? = null

    inner class ProdutoViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        val produtoItemView: TextView = itemView.findViewById(R.id.tvProduto)
        val produtoQtdView: TextView = itemView.findViewById(R.id.tvquantity)
        val produtoValueView: TextView = itemView.findViewById(R.id.tvvalue)
        val produtoIdView: TextView = itemView.findViewById(R.id.tvid)
        val buttonDeleteView: ImageButton = itemView.findViewById(R.id.btDelete)
        val buttonUpdateNav: AppCompatImageView = itemView.findViewById(R.id.btUpdNav)
        val rootView: View = itemView.rootView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProdutoViewHolder {
        val itemView = inflater.inflate(R.layout.product_item, parent, false)
        return ProdutoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val current = produtos[position]
        holder.produtoItemView.text = current.description
        holder.produtoQtdView.text = current.quantity.toString()
        holder.produtoValueView.text = current.value.toString()
        holder.produtoIdView.text = current.id

        holder.buttonDeleteView.setOnClickListener {
                removeItem(position)
        }

        holder.buttonUpdateNav.setOnClickListener {
            val product : Product = this.produtos[position]

            ProductUpdate.quantity = product.quantity
            ProductUpdate.description = product.description
            ProductUpdate.value = product.value
            ProductUpdate.id = product.id

            holder.rootView.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

    }
    internal fun setProducts(produtos: MutableList<Product> = mutableListOf<Product>()) {
        this.produtos = produtos
        notifyDataSetChanged()
    }

    internal fun setModel(listViewModel: ListViewModel) {
        this.listViewModel = listViewModel
    }

    override fun getItemCount() = produtos.size


    private fun removeItem(position: Int){

        Builder(this.inflater.context)
                .setTitle("Products")
                .setMessage("Confirm Delete?")
                .setPositiveButton("Yes") { dialog, _ ->

                    val product : Product = this.produtos[position]

                    this.listViewModel?.delete(product.id)

                    this.produtos.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()

                    dialog.dismiss()
                }
                .setNegativeButton("No") {
                    dialog, _ -> dialog.dismiss()
                }
                .create().show()
    }

}