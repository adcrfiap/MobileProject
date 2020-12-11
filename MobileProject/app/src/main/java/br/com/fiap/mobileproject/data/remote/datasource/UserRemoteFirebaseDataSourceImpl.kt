package br.com.fiap.mobileproject.data.remote.datasource

import android.content.ContentValues.TAG
import android.util.Log
import br.com.fiap.mobileproject.data.remote.mapper.NewUserFirebasePayloadMapper
import br.com.fiap.mobileproject.domain.entity.*
import br.com.fiap.mobileproject.domain.exceptions.UserNotLoggedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRemoteFirebaseDataSourceImpl(
        val firebaseAuth: FirebaseAuth,
        private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<User> {
        return try {
            firebaseAuth.currentUser?.reload()
            val firebaseUser = firebaseAuth.currentUser

            val user = getUser(firebaseUser?.email, firebaseUser?.uid)

            if (firebaseUser == null)
                RequestState.Error(UserNotLoggedException())
            else
                RequestState.Success(user)

        }catch (e: UserNotLoggedException){
            RequestState.Error(e)
        }

    }

    private suspend fun getUser(email: String?, uid: String?): User {
        var user: User = User("","","")

        firebaseFirestore
            .collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    user = User(document.get("name") as String,
                                document.get("email") as String,
                                document.id
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }.await()

        return user;
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(
                    userLogin.email,
                    userLogin.password
            ).await()

            val firebaseUser = firebaseAuth.currentUser
            val user = getUser(firebaseUser?.email, firebaseUser?.uid)

            if (firebaseUser == null)
                RequestState.Error(UserNotLoggedException())
            else
                RequestState.Success(user)

        }catch (e: Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun resetPassword(email: String): RequestState<String> {
        return try{
            firebaseAuth.sendPasswordResetEmail(email).await()
            RequestState.Success("Check your mail box")
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()

            //Mapeamento para não salvar o password no banco
            val newUserFirebasePayload =
                    NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(newUser)

            val userId = firebaseAuth.currentUser?.uid

            if (userId == null) {
                RequestState.Error(java.lang.Exception("Not able to create account"))
            } else {
                firebaseFirestore
                        .collection("users")
                        .document(userId)
                        .set(newUserFirebasePayload)
                        .await()
                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload, userId))
            }
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun signout(): RequestState<User> {
        return  try {
            firebaseAuth.currentUser?.reload()
            val firebaseUser = firebaseAuth.currentUser
            val user = getUser(firebaseUser?.email, firebaseUser?.uid)

            firebaseAuth.signOut()

            RequestState.Success(user)

        }catch (e: java.lang.Exception){
            RequestState.Error(e)
        }

    }
}