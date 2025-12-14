package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.companyname.zd7_v8_kma.Login.MainDb
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.companyname.zd7_v8_kma.Login.ClientEntity
import com.companyname.zd7_v8_kma.Login.WorkerEntity

class InfoFragment : Fragment() {

    private lateinit var db: MainDb
    private var furnitureId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        furnitureId = requireArguments().getInt("clientId")

        val tvName = view.findViewById<TextView>(R.id.name_d)
        val tvModel = view.findViewById<TextView>(R.id.model)
        val tvSeller = view.findViewById<TextView>(R.id.worker)
        val tvPrice = view.findViewById<TextView>(R.id.price)
        val etCount = view.findViewById<EditText>(R.id.edit_count)
        val btnBuy = view.findViewById<Button>(R.id.button)

        lifecycleScope.launch {
            val furniture = db.getWorkerDao().getById(furnitureId)
            val seller = db.getLoginDao().getById(furniture.login)

            tvName.text = furniture.furniture
            tvModel.text = furniture.model
            tvSeller.text = "Продавец: ${seller?.login}"

            etCount.doOnTextChanged { text, _, _, _ ->
                val count = text?.toString()?.toIntOrNull() ?: 0
                tvPrice.text = "Цена: ${count * furniture.price} ₽"
            }

            btnBuy.setOnClickListener {
                buyFurniture(furniture, etCount.text.toString())
            }
        }
    }

    private fun buyFurniture(furniture: WorkerEntity, countStr: String) {
        val count = countStr.toIntOrNull() ?: return
        val userId = getCurrentLoginId()

        lifecycleScope.launch {
            db.getClientDao().insert(
                ClientEntity(
                    login = userId,
                    orderId = furniture.id,
                    company = furniture.login,
                    count = count,
                    price= furniture.price * count
                )
            )

            Toast.makeText(requireContext(), "Покупка оформлена", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentLoginId(): Int {
        return requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)
            .getInt("loginId", -1)
    }
}