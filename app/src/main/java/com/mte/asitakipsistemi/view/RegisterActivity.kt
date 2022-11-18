package com.mte.asitakipsistemi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.mte.asitakipsistemi.R
import com.mte.asitakipsistemi.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        binding.uyekaydetbuton.setOnClickListener{

            //Kaydetme butonu ile Kaydetme Adımları

            var uyeadsoyad=binding.uyeadsoyad.text.toString()
            var uyetc=binding.uyetc.text.toString()
            var uyetelefon=binding.uyetelefon.text.toString()
            var uyesifre=binding.uyesifre.text.toString()
            var uyemail=binding.uyemail.text.toString()
            if(TextUtils.isEmpty(uyeadsoyad)){
                binding.uyeadsoyad.error="Lütfen Adınızı ve Soyadınızı Giriniz"
                return@setOnClickListener

            }else if(TextUtils.isEmpty(uyetc)){
                binding.uyetc.error="Lütfen Tc Kimlik Numaranızı Giriniz"
                return@setOnClickListener

            }else  if(TextUtils.isEmpty(uyetelefon)){
                binding.uyeadsoyad.error="Lütfen Telefon Numaranızı Giriniz"
                return@setOnClickListener

            }else if(TextUtils.isEmpty(uyesifre)){
                binding.uyeadsoyad.error="Lütfen Şifre Belirleyiniz"
                return@setOnClickListener

            }else if(TextUtils.isEmpty(uyemail)) {
                binding.uyeadsoyad.error = "Lütfen Şifre Belirleyiniz"
                return@setOnClickListener
            }
            //Üye Bilgilerini veri tabanına ekleme
            auth.createUserWithEmailAndPassword(binding.uyemail.text.toString(),binding.uyesifre.text.toString())
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {

                            Toast.makeText(this,"Mailinizi Kontrol Ediniz",Toast.LENGTH_LONG).show()
                        }?.addOnFailureListener{
                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                        }
                        //şunaki kullanıcı bilgilerini alıyorum
                        var currentUser=auth.currentUser
                        // kullanıcı id sini alıp kullanıcı bilgilerini kayıt ediyorum.
                        var currentUserDb=currentUser?.let { it1-> databaseReference?.child(it1.uid) }
                        currentUserDb?.child("adisoyadı")?.setValue(binding.uyeadsoyad.text.toString())
                        currentUserDb?.child("uyetc")?.setValue(binding.uyetc.text.toString())
                        currentUserDb?.child("uyemail")?.setValue(binding.uyemail.text.toString())
                        currentUserDb?.child("uyetelefon")?.setValue(binding.uyetelefon.text.toString())
                        Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_LONG).show()

                        auth.signOut()
                    }else{
                        Toast.makeText(this,"Kayıt Hatalı ",Toast.LENGTH_LONG).show()
                    }
                }


        }
        binding.uyegirisbuton.setOnClickListener{
            intent= Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}