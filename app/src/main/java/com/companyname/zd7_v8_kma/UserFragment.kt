package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.ClientDao
import com.companyname.zd7_v8_kma.Login.MainDb
import com.companyname.zd7_v8_kma.Login.SupplierDao
import com.companyname.zd7_v8_kma.Login.WorkerDao
import com.companyname.zd7_v8_kma.Login.WorkerEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    private lateinit var dao: WorkerDao
    private lateinit var clientDao: ClientDao
    private lateinit var db: MainDb
    private lateinit var adapter: FurnitureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        dao = MainDb.getDb(requireContext()).getWorkerDao()
        clientDao = MainDb.getDb(requireContext()).getClientDao()

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        lifecycleScope.launch {
            val details = dao.getAllDetailNames()

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

        adapter = FurnitureAdapter(emptyList()) { furniture ->
            val bundle = Bundle().apply {
                putInt("clientId", furniture.id)
            }

            findNavController().navigate(
                R.id.action_userFragment_to_infoFragment,
                bundle
            )
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        lifecycleScope.launch {
            val list = dao.getAll()
            adapter.updateData(list)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                val selectedDetail = p0.getItemAtPosition(p2).toString()

                lifecycleScope.launch {
                    val list = dao.getByDetail(selectedDetail)
                    adapter.updateData(list)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_listFragment)
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        return prefs.getInt("loginId", -1)
    }
}