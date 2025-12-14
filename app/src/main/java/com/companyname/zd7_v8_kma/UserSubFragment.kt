package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.MainDb
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UserSubFragment : Fragment() {

    private lateinit var db: MainDb
    private lateinit var adapter: SupplierAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_user_sub, container, false)

        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_userSubFragment_to_addDetailsFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerD)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = SupplierAdapter(emptyList()) { selectedSupplier ->
            val bundle = Bundle().apply {
                putInt("supplierId", selectedSupplier.id)
            }

            findNavController().navigate(
                R.id.action_userSubFragment_to_editSupplierFragment,
                bundle
            )
        }
        recycler.adapter = adapter

        loadData()
    }

    private fun loadData() {
        val loginId = getCurrentLoginId()

        Log.d("UserSubFragment", "loginId = $loginId")

        lifecycleScope.launch {
            val list = db.getSupplierDao().getByLoginId(loginId)
            Log.d("UserSubFragment", "list size = ${list.size}")
            adapter.updateData(list)
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        return prefs.getInt("loginId", -1)
    }
}