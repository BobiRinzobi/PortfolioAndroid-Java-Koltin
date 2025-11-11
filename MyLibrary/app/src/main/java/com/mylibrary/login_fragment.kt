package com.mylibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.navigation.fragment.findNavController


class login_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val regbtn: Button = view.findViewById(R.id.reg_btn)
        val loginbtn : Button = view.findViewById(R.id.login_btn)

        val checkbtn : Button = view.findViewById(R.id.check_btn)

        checkbtn.setOnClickListener { findNavController().navigate(R.id.action_login_fragment_to_search_fragment) }

        regbtn.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_registration_fragment)
        }

        val emailinput : EditText = view!!.findViewById(R.id.email_login)
        val passwordinput : EditText = view!!.findViewById(R.id.password_login)

        loginbtn.setOnClickListener {
            val email : String = emailinput.text.toString().trim()
            val password : String = passwordinput.text.toString().trim()
            if(checkRegistration(email, password))
            {

                val db = DbHelper(requireContext(),null)
                val Auth = db.getUser(email,password)
                if (Auth)
                {
                    Toast.makeText(requireContext(),"пользователь авторизован!", LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_login_fragment_to_main_fragment)

                }


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