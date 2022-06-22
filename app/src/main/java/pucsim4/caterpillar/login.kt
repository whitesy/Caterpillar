package pucsim4.caterpillar

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pucsim4.caterpillar.databinding.ActivityGameBinding
import pucsim4.caterpillar.databinding.ActivityLoginBinding

class login : AppCompatActivity(){
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        //configure google signing
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("472321478587-o9j5vj036egsp6q7fjufdluacfm29la5.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn () {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG,"firebaseAuthWithGoogle:"+account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e:ApiException){
                Log.w(ContentValues.TAG,"Google sign in failed",e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d(ContentValues.TAG,"signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Log.d(ContentValues.TAG,"signInWithCredential:failure",task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra(EXTRA_NAME,user.displayName)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA NAME"

    }
}