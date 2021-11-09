package com.example.samplecoroutinetask.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.samplecoroutinetask.R
import com.example.samplecoroutinetask.fragments.MainActivityFragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var callbackManager: CallbackManager? = null
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        signinbtn.setOnClickListener {
            if (!TextUtils.isEmpty(email.text) && !TextUtils.isEmpty(password.text)) {
                val emailText = email.text.toString().trim()
                if (emailText.isValidEmail()) {
                    if (password.text.length > 6) {
                        if (email.hasFocus()) {
                            email.clearFocus()
                        }
                        if (password.hasFocus()) {
                            password.clearFocus()
                        }
                        email.setText("")
                        password.setText("")

                        startActivity(Intent(applicationContext, MainActivityFragment::class.java))
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Password length too short min 6",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Invalid email format",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Email or password should not be null",
                    Toast.LENGTH_SHORT
                ).show()
            }

            /*val destination = ActivityNavigator(this).createDestination()
                .setIntent(Intent(this, MainActivity::class.java))
            ActivityNavigator(this).navigate(destination, null, null, null)*/
        }

        googlesignin.setOnClickListener {
            signInGoogle()
        }

        fbsignin.setOnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
//                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        handleFacebookAccessToken(loginResult.accessToken)
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError: " + error.message)
                    }
                })
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("facebookauth", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("facebookAuth", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this, MainActivityFragment::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("facebookAuth", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
//            firebaseAuthWithGoogle(account!!)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                updateUI(account)
            }
        } catch (e: ApiException) {
            Log.d("ApiException", "handleResult: $e")
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                SavedPreference.setEmail(this, account.email!!.toString())
                SavedPreference.setUsername(this, account.displayName!!.toString())
                val intent = Intent(this, MainActivityFragment::class.java)
                startActivity(intent)

//                val destination = ActivityNavigator(this).createDestination()
//                    .setIntent(Intent(this, MainActivity::class.java))
//                ActivityNavigator(this).navigate(destination, null, null, null)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val accessToken = AccessToken.getCurrentAccessToken()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, MainActivityFragment::class.java))
//            val destination = ActivityNavigator(this).createDestination()
//                .setIntent(Intent(this, MainActivity::class.java))
//            ActivityNavigator(this).navigate(destination, null, null, null)
            finish()
        }
        if(accessToken != null){
            startActivity(Intent(this, MainActivityFragment::class.java))
            finish()
        }
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}