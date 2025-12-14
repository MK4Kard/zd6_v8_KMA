package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.SupplierDao
import com.companyname.zd7_v8_kma.Login.SupplierEntity
import com.companyname.zd7_v8_kma.Login.WorkerDao
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.FurnitureDetailDao
import com.companyname.zd7_v8_kma.Login.FurnitureDetailEntity
import com.companyname.zd7_v8_kma.Login.FurnitureSupplierEntity
import com.companyname.zd7_v8_kma.Login.WorkerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFurnitureFragment : Fragment() {

    private lateinit var dao: WorkerDao
    private lateinit var supplierDao: SupplierDao
    private lateinit var db: MainDb
    private var currentFurnitureId: Int = -1
    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_furniture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        dao = MainDb.getDb(requireContext()).getWorkerDao()
        supplierDao = MainDb.getDb(requireContext()).getSupplierDao()

        val detail = view.findViewById<EditText>(R.id.edit_name)
        val model = view.findViewById<EditText>(R.id.edit_model)
        val count = view.findViewById<EditText>(R.id.edit_count)
        val price = view.findViewById<EditText>(R.id.edit_price)

        val button = view.findViewById<AppCompatButton>(R.id.button)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        lifecycleScope.launch {
            val details = supplierDao.getAllDetailNames()

            val adapterS =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, details)
            adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapterS
        }

        val loginId = getCurrentLoginId()

        if (loginId == -1) {
            Snackbar.make(requireView(), "Пользователь не авторизован", Snackbar.LENGTH_LONG).show()
            return
        }

        button.setOnClickListener {

            if (detail.text.toString().isNotEmpty() &&
                model.text.toString().isNotEmpty() &&
                count.text.toString().isNotEmpty() &&
                price.text.toString().isNotEmpty()){
                lifecycleScope.launch {
                    val exist = dao.findSame(
                        loginId = loginId,
                        name = detail.text.toString(),
                        model = model.text.toString(),
                        price = price.text.toString().toDouble()
                    )
                    if (exist != null) {
                        exist.count += count.text.toString().toInt()
                        dao.update(exist)
                    } else {
                        val entity = WorkerEntity(
                            login = loginId,
                            furniture = detail.text.toString(),
                            model = model.text.toString(),
                            count = count.text.toString().toInt(),
                            price = price.text.toString().toDouble()
                        )

                        val id = dao.insert(entity).toInt()
                        currentFurnitureId = id
                    }
                }
            } else {
                Snackbar.make(requireView(), "Пустые поля", Snackbar.LENGTH_LONG).show()
            }
        }

        adapter = DetailAdapter(emptyList()) { supplier ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (supplier.count > 0) {
                    val newCount = supplier.count - 1
                    supplierDao.update(
                        supplier.copy(count = newCount)
                    )
                }

                val exist = furnitureDetailDao.getOne(
                    furnitureId = currentFurnitureId,
                    detailId = supplier.id
                )

                if (exist != null) {
                    furnitureDetailDao.update(exist.copy(count = exist.count + 1))
                } else {
                    furnitureDetailDao.insert(
                        FurnitureDetailEntity(
                            furnitureId = currentFurnitureId,
                            detailId = supplier.id,
                            count = 1
                        )
                    )
                    val fs = FurnitureSupplierEntity(
                        furnitureId = currentFurnitureId,
                        supplierId = supplier.login // loginId поставщика
                    )
                    db.getFurnitureSupplierDao().insert(fs)

                }
                val updatedList = supplierDao.getByDetail(supplier.detail)
                launch(Dispatchers.Main) {
                    adapter.updateData(updatedList)
                }
            }
        }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                val selectedDetail = p0.getItemAtPosition(p2).toString()

                lifecycleScope.launch {
                    val list = supplierDao.getByDetail(selectedDetail)
                    adapter.updateData(list)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        return prefs.getInt("loginId", -1)
    }

    private val furnitureDetailDao by lazy {
        db.getFurnitureDetailDao()
    }
}