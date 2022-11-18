package com.mte.asitakipsistemi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mte.asitakipsistemi.R
import com.mte.asitakipsistemi.databinding.FragmentHesapBinding


class HesapFragment : Fragment() {

    private var _binding : FragmentHesapBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding=FragmentHesapBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentuser=auth.currentUser

        var userReference=databaseReference?.child(currentuser?.uid!!)
        userReference?.addValueEventListener( object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.editTextEposta.hint=snapshot.child("uyemail").value.toString()
                binding.editTextAd.hint=snapshot.child("adisoyadı").value.toString()
                binding.editTextTc.hint=snapshot.child("uyetc").value.toString()
                binding.editTextTelefon.hint=snapshot.child("uyetelefon").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        binding.cikisYap.setOnClickListener { auth.signOut()
            val intent= Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}