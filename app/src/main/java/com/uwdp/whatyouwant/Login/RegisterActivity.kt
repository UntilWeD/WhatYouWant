package com.uwdp.whatyouwant.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uwdp.whatyouwant.databinding.ActivityReviewBinding
import com.uwdp.whatyouwant.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth=Firebase.auth
        binding= ActivityRegisterBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener{
            val email : String = binding.etEmail.text.toString()
            val password : String = binding.etPassword.text.toString()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task->
                if(task.isSuccessful) {
                    Toast.makeText(this,"회원가입이 완료되셨습니다. ",Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this,"회원가입을 실패하셨습니다. ",Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnQuit.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }
}