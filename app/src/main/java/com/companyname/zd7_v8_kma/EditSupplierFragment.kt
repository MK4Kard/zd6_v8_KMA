package com.companyname.zd7_v8_kma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.SupplierEntity
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class EditSupplierFragment : Fragment() {

    private lateinit var db: MainDb
    private var supplierId: Int = 0
    private lateinit var currentSupplier: SupplierEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supplierId = requireArguments().getInt("supplierId", -1)

        if (supplierId == -1) {
            throw IllegalStateException("supplierId not passed")
        }

        db = MainDb.getDb(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_supplier, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailEdit = view.findViewById<EditText>(R.id.edit_name)
        val countEdit = view.findViewById<EditText>(R.id.edit_count)
        val priceEdit = view.findViewById<EditText>(R.id.edit_price)
        val charEdit = view.findViewById<EditText>(R.id.edit_char)

        lifecycleScope.launch {
            currentSupplier = db.getSupplierDao().getById(supplierId)
                ?: throw IllegalStateException("Supplier not found: id=$supplierId")

            detailEdit.setText(currentSupplier.detail)
            countEdit.setText(currentSupplier.count.toString())
            priceEdit.setText(currentSupplier.price.toString())
            charEdit.setText(currentSupplier.characteristics.toString())
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            lifecycleScope.launch {
                currentSupplier.detail = detailEdit.text.toString()
                currentSupplier.count = countEdit.text.toString().toInt()
                currentSupplier.price = priceEdit.text.toString().toDouble()
                currentSupplier.characteristics = charEdit.text.toString()
                db.getSupplierDao().update(currentSupplier)
                findNavController().popBackStack()
            }
        }

        view.findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            lifecycleScope.launch {
                db.getSupplierDao().delete(currentSupplier)
                findNavController().popBackStack()
            }
        }
    }

}