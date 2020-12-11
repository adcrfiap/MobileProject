package br.com.fiap.mobileproject.data.remote.datasource

import android.content.ContentValues
import android.util.Log
import br.com.fiap.mobileproject.data.remote.mapper.NewUserFirebasePayloadMapper
import br.com.fiap.mobileproject.domain.entity.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRemoteFirebaseDataSourceImpl
(
        val firebaseAuth: FirebaseAuth,
        private val firebaseFirestore: FirebaseFirestore
): ProductRemoteDataSource{
    override suspend fun listProducts(): RequestState<MutableList<Product>> {
            var p = Product("",0,"",0,"")
            var queryResult: MutableList<Product> = mutableListOf<Product>()

        firebaseFirestore
                .collection("products")
                .whereEqualTo("userid", firebaseAuth.uid)
                .get()
                .addOnSuccessListener {  products ->
                    for (product in products ){
                        p = Product(product.get("description") as String,
                                product.get("quantity") as Number,
                                product.get("userid") as String,
                                product.get("value") as Number,
                                product.id)

                        queryResult.add(p)}
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }.await()

            return RequestState.Success(queryResult)

    }

    override suspend fun create(newProduct: NewProduct): RequestState<NewProduct> {

        return try {
            newProduct.userid = firebaseAuth.uid.toString()

            firebaseFirestore
                .collection("products")
                .document()
                .set(newProduct)
                .await()

            RequestState.Success(newProduct)

        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }

    }

    override suspend fun delete(id: String): RequestState<String> {

        return try {

            firebaseFirestore
                    .collection("products")
                    .document(id)
                    .delete()
                    .await()

            RequestState.Success("Product removed")

        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }

    }

    override suspend fun find(description: String): RequestState<MutableList<Product>> {

        var p = Product("",0,"",0,"")
        var queryResult: MutableList<Product> = mutableListOf<Product>()

        firebaseFirestore
            .collection("products")
            .whereEqualTo("description", description)
            .get()
            .addOnSuccessListener {  products ->
                for (product in products ){
                    p = Product(product.get("description") as String,
                        product.get("quantity") as Number,
                        product.get("userid") as String,
                        product.get("value") as Number,
                        product.id)

                    queryResult.add(p)}
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }.await()

        return RequestState.Success(queryResult)

    }


    override suspend fun update(): RequestState<String> {

        return try {
            val newProduct = NewProduct(ProductUpdate.description,
                                        ProductUpdate.quantity,
                                        firebaseAuth.uid.toString(),
                                        ProductUpdate.value )

            firebaseFirestore
                    .collection("products")
                    .document(ProductUpdate.id)
                    .set(newProduct)
                    .await()

            RequestState.Success("Product Update!")

        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }

    }

}
