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
import androidx.room.Room
import com.companyname.zd7_v8_kma.Login.LoginDb
import com.companyname.zd7_v8_kma.Login.LoginEntity
import com.google.android.material.snackbar.Snackbar

class SignFragment : Fragment() {

    private val DATABASE_NAME: String = "logins"
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

        mail = view.findViewById(R.id.edit_mail)
        login = view.findViewById(R.id.edit_login)
        password = view.findViewById(R.id.edit_password)

        spinner = view.findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(requireContext(), R.array.users,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val db = Room.databaseBuilder(requireContext(), LoginDb::class.java,
            DATABASE_NAME).build()
        val logDao = db.getLoginDao()
        val log: List<LoginEntity> = logDao.getAll()

        val navigateButton: Button = view.findViewById(R.id.button)

        navigateButton.setOnClickListener {
            if (mail.text.isNotEmpty() && login.text.isNotEmpty() && password.text.isNotEmpty()) {
                findNavController().navigate(R.id.action_signFragment_to_userFragment)

            }
            else {
                Snackbar.make(view, "Заполните поля!", Snackbar.LENGTH_SHORT).show()
            }
        }

        return view
    }
}