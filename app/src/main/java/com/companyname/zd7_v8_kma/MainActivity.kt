package com.companyname.zd7_v8_kma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import com.companyname.zd7_v8_kma.Login.LoginDb
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    /*val database = LoginDb.getDb(this)
    lateinit var mail: EditText
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var spinner: Spinner*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        mail = findViewById<EditText>(R.id.edit_mail)
        login = findViewById<EditText>(R.id.edit_login)
        password = findViewById<EditText>(R.id.edit_password)

        spinner = findViewById<Spinner>(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.users,
            android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }*/
    }

    /*fun Login(view: View){
        if (mail.text.isNotEmpty() && login.text.isNotEmpty() && password.text.isNotEmpty()){
            Save(mail.text.toString(), login.text.toString(),
                password.text.toString(), spinner.selectedItem.toString())
        }
        else {
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun Save(mail: String, login: String, pass: String, user: String){
        val sharedPreferences = getSharedPreferences("User", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("mail", mail)
        editor.putString("login", login)
        editor.putString("password", pass)
        editor.putString("user", user)
    }*/

    /*database.getFoodDao().insert(
    FoodEntity(
    title = title,
    image = image,
    amount = amount,
    unit = unit
    )
    )*/
}