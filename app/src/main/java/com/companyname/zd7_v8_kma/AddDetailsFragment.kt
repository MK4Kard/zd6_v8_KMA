package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.companyname.zd7_v8_kma.Login.LoginDao
import com.companyname.zd7_v8_kma.Login.LoginEntity
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.SupplierDao
import com.companyname.zd7_v8_kma.Login.SupplierEntity
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddDetailsFragment : Fragment() {

    private lateinit var dao: SupplierDao
    private lateinit var db: MainDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = MainDb.getDb(requireContext()).getSupplierDao()

        val detail = view.findViewById<EditText>(R.id.edit_name)
        val char = view.findViewById<EditText>(R.id.edit_char)
        val count = view.findViewById<EditText>(R.id.edit_count)
        val price = view.findViewById<EditText>(R.id.edit_price)

        val button = view.findViewById<AppCompatButton>(R.id.button)

        val loginId = getCurrentLoginId()

        if (loginId == -1) {
            Snackbar.make(requireView(), "Пользователь не авторизован", Snackbar.LENGTH_LONG).show()
            return
        }

        button.setOnClickListener {

            if (detail.text.toString().isNotEmpty() &&
                char.text.toString().isNotEmpty() &&
                count.text.toString().isNotEmpty() &&
                price.text.toString().isNotEmpty()){
                lifecycleScope.launch {
                    val exist = dao.findSame(
                        loginId = loginId,
                        detail = detail.text.toString(),
                        characteristics = char.text.toString(),
                        price = price.text.toString().toDouble()
                    )
                    if (exist != null) {
                        exist.count += count.text.toString().toInt()
                        dao.update(exist)
                    } else {
                        val entity = SupplierEntity(
                            login = loginId,
                            detail = detail.text.toString(),
                            characteristics = char.text.toString(),
                            count = count.text.toString().toInt(),
                            price = price.text.toString().toDouble()
                        )

                        dao.insert(entity)
                    }
                }
            } else {
                Snackbar.make(requireView(), "Пустые поля", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        return prefs.getInt("loginId", -1)
    }
}