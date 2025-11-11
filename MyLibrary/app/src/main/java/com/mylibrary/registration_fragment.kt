package com.mylibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.*
import androidx.navigation.fragment.findNavController
import kotlin.toString



class registration_fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        val reg_btn: Button = view.findViewById(R.id.btnRegister)

        val emailinput : EditText = view.findViewById(R.id.email)
        val passwordinput : EditText = view.findViewById(R.id.password)


        reg_btn.setOnClickListener {
            val email : String = emailinput.text.toString().trim()
            val password : String = passwordinput.text.toString().trim()
            if(checkRegistration(email, password))
            {
                val user : User = User(email = email, password = password)
                val db = DbHelper(requireContext(),null)
                db.addUser(user)
                Toast.makeText(requireContext(),"пользователь добавлен ${user.email}", LENGTH_LONG).show()

                findNavController().navigate(R.id.action_registration_fragment_to_login_fragment)

            }
            }

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkRegistration(email : String, password : String) : Boolean
    {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(),"не все поля заполнены $email | $password", LENGTH_LONG).show()
            return false
        }
        if(!isValidEmail(email))
        {
            Toast.makeText(requireContext(),"Некорректно введена почта", LENGTH_LONG).show()
            return false
        }
        return true
    }
}