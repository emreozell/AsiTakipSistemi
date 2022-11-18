package com.mte.asitakipsistemi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mte.asitakipsistemi.R
import com.mte.asitakipsistemi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        // kullanıcı girip yaptığını kontrol ediyorum
        var currentUser=auth.currentUser
        /*if (currentUser!=null){

             startActivity(Intent(this,MainActivity::class.java))
             finish()
         }*/
        // giriş yap butonuna tıklandığında
        binding.girisyap.setOnClickListener{

            var girisemail=binding.email.text.toString()
            var girissifre=binding.sifre.text.toString()
            if(TextUtils.isEmpty(girisemail)){
                binding.email.error="Lütfen email adresinizi giriniz"
                return@setOnClickListener
            }else if(TextUtils.isEmpty(girissifre)){
                binding.sifre.error="Lütfen şifrenizi giriniz"
                return@setOnClickListener
            }
            //giris bilgilerini doğrulama
            auth.signInWithEmailAndPassword(girisemail,girissifre)
                .addOnCompleteListener(this) {
                    if(it.isSuccessful){
                        val verification = auth.currentUser?.isEmailVerified
                        if(verification==true) {
                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{

                            Toast.makeText(this,"Öncelikle Mail Adresinizi Doğrulamalısınız",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Giriş hatalı tekrar deneyiniz",Toast.LENGTH_SHORT).show()
                    }
                }

        }
        //kayıt ol a git
        binding.kayitol.setOnClickListener{
            intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}