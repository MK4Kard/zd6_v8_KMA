package com.companyname.zd7_v8_kma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.companyname.zd7_v8_kma.Login.LoginDb

class SignFragment : Fragment() {

    lateinit var mail: EditText
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sign, container, false)

        spinner = view.findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(requireContext(), R.array.users,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val navigateButton: Button = view.findViewById(R.id.button)
        navigateButton.setOnClickListener {
            findNavController().navigate(R.id.action_signFragment_to_userFragment)
        }

        return view
    }

    /*fun Save(mail: String, login: String, pass: String, user: String){
        val sharedPreferences = getSharedPreferences("User", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("mail", mail)
        editor.putString("login", login)
        editor.putString("password", pass)
        editor.putString("user", user)
    }*/
}