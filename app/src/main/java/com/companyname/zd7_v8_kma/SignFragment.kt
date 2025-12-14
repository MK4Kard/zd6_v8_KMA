package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.companyname.zd7_v8_kma.Login.LoginDao
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.LoginEntity
import com.google.android.material.snackbar.Snackbar

class SignFragment : Fragment() {

    private lateinit var db: MainDb
    private lateinit var loginDao: LoginDao

    private lateinit var mail: EditText
    private lateinit var login: EditText
    private lateinit var password: EditText

    var check: Boolean = true
    private var currentUserType: Int = 0

    private var selectedUser = "Клиент"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  inflater.inflate(R.layout.fragment_sign, container, false)

        db = MainDb.getDb(requireContext())
        loginDao = db.getLoginDao()

        mail = view.findViewById(R.id.edit_mail)
        login = view.findViewById(R.id.edit_login)
        password = view.findViewById(R.id.edit_password)

        val button: Button = view.findViewById(R.id.button3)

        button.setOnClickListener {
            if (mail.text.isNotEmpty() && login.text.isNotEmpty() && password.text.isNotEmpty()) {
                lifecycleScope.launch {
                    saveLog()
                    if (check)
                        when (currentUserType) {
                            0 -> findNavController().navigate(R.id.action_signFragment_to_userFragment)
                            1 -> findNavController().navigate(R.id.action_signFragment_to_userWorkFragment)
                            2 -> findNavController().navigate(R.id.action_signFragment_to_userSubFragment)
                        }
                }
            } else {
                Snackbar.make(view, "Заполните поля", Snackbar.LENGTH_LONG).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.text)
        val button1: Button = view.findViewById(R.id.button)
        val button2: Button = view.findViewById(R.id.button2)
        val button3: Button = view.findViewById(R.id.button3)

        val buttons = listOf(button1, button2, button3)
        val texts = listOf("Клиент", "Работник", "Поставщик")
        val backgrounds = listOf(R.drawable.btn_grey, R.drawable.btn, R.drawable.btn_orange)

        val carousel = view.findViewById<Carousel>(R.id.carousel)

        val adapter = object : Carousel.Adapter {
            override fun count(): Int = buttons.size
            override fun populate(view: View, index: Int) {
                val button = view as Button
                button.setBackgroundResource(backgrounds[index])
            }

            override fun onNewItem(index: Int) {
                textView.text = texts[index]
                selectedUser = texts[index]
                currentUserType = index
            }
        }

        carousel.setAdapter(adapter)
    }

    private suspend fun saveLog() {
        val mailText = mail.text.toString()
        val loginText = login.text.toString()
        val passwordText = password.text.toString()

        val users = loginDao.getAll()
        val existUser = users.find { it.mail == mailText }

        if (existUser != null) {
            when {
                existUser.login != loginText -> {
                    Snackbar.make(requireView(), "Неверный логин", Snackbar.LENGTH_LONG).show()
                    check = false
                    return
                }
                existUser.password != passwordText -> {
                    Snackbar.make(requireView(), "Неверный пароль", Snackbar.LENGTH_LONG).show()
                    check = false
                    return
                }
                existUser.user != selectedUser -> {
                    Snackbar.make(requireView(), "Вы зарегестрированы другим профилем", Snackbar.LENGTH_LONG).show()
                    check = false
                    return
                }
                else -> {
                    check = true
                    saveUserSession(existUser)
                }
            }
        }
        else {
            val log = LoginEntity(
                mail = mailText,
                login = loginText,
                password = passwordText,
                user = selectedUser
            )

            loginDao.insert(log)

            val newUser = loginDao.getAll().find { it.mail == mailText }
            check = newUser != null
            newUser?.let { saveUserSession(it) }
        }
    }

    private fun saveUserSession(user: LoginEntity) {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        prefs.edit()
            .putInt("loginId", user.id)
            .putString("login", user.login)
            .putString("userType", user.user)
            .apply()
    }
}
