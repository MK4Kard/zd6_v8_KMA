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
import com.companyname.zd7_v8_kma.Login.MainDb
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.ClientDao
import kotlinx.coroutines.launch

class UserWorkFragment : Fragment() {

    private lateinit var db: MainDb
    private lateinit var adapter: FurnitureAdapter
    private lateinit var clientDao: ClientDao
    private lateinit var buyerAdapter: BuyerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_user_work, container, false)

        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_userWorkFragment_to_addFurnitureFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FurnitureAdapter(emptyList()) { selectedFurniture ->
            val bundle = Bundle().apply {
                putInt("furnitureId", selectedFurniture.id)
            }

            findNavController().navigate(
                R.id.action_userWorkFragment_to_editFurnitureFragment,
                bundle
            )
        }
        recycler.adapter = adapter

        clientDao = db.getClientDao()

        val recyclerB = view.findViewById<RecyclerView>(R.id.recyclerB)
        recyclerB.layoutManager = LinearLayoutManager(requireContext())
        buyerAdapter = BuyerAdapter(emptyList())
        recyclerB.adapter = buyerAdapter

        loadBuyers()

        loadData()
    }

    private fun loadData() {
        val loginId = getCurrentLoginId()
        if (loginId == -1) {
            return
        }

        lifecycleScope.launch {
            val list = db.getWorkerDao().getByLoginId(loginId)
            Log.d("UserWorkFragment", "list size = ${list.size}")
            adapter.updateData(list)
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        return prefs.getInt("loginId", -1)
    }

    private fun loadBuyers() {
        val workerId = getCurrentLoginId() // id текущего продавца
        lifecycleScope.launch {
            val buyers = clientDao.getClientsByWorker(workerId)
            buyerAdapter.updateData(buyers)
        }
    }
}