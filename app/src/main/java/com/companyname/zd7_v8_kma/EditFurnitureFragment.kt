package com.companyname.zd7_v8_kma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.SupplierEntity
import com.companyname.zd7_v8_kma.Login.WorkerEntity
import kotlinx.coroutines.launch

class EditFurnitureFragment : Fragment() {

    private lateinit var db: MainDb
    private var furnitureId: Int = 0
    private lateinit var currentFurniture: WorkerEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        furnitureId = requireArguments().getInt("furnitureId", -1)

        if (furnitureId == -1) {
            throw IllegalStateException("furnitureId not passed")
        }

        db = MainDb.getDb(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_furniture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEdit = view.findViewById<EditText>(R.id.edit_name)
        val countEdit = view.findViewById<EditText>(R.id.edit_count)
        val priceEdit = view.findViewById<EditText>(R.id.edit_price)
        val modelEdit = view.findViewById<EditText>(R.id.edit_model)

        lifecycleScope.launch {
            currentFurniture = db.getWorkerDao().getById(furnitureId)
                ?: throw IllegalStateException("Supplier not found: id=$furnitureId")

            nameEdit.setText(currentFurniture.furniture)
            countEdit.setText(currentFurniture.count.toString())
            priceEdit.setText(currentFurniture.price.toString())
            modelEdit.setText(currentFurniture.model)
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            lifecycleScope.launch {
                currentFurniture.furniture = nameEdit.text.toString()
                currentFurniture.count = countEdit.text.toString().toInt()
                currentFurniture.price = priceEdit.text.toString().toDouble()
                currentFurniture.model = modelEdit.text.toString()
                db.getWorkerDao().update(currentFurniture)
                findNavController().popBackStack()
            }
        }

        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            lifecycleScope.launch {
                db.getWorkerDao().delete(currentFurniture)
                findNavController().popBackStack()
            }
        }
    }
}