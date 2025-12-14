package com.companyname.zd7_v8_kma

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.MainDb
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var db: MainDb
    private lateinit var adapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MainDb.getDb(requireContext())
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = ClientAdapter(emptyList())
        recycler.adapter = adapter

        val loginId = getCurrentLoginId()
        lifecycleScope.launch {
            val purchases = db.getClientDao().getByLoginId(loginId)
            adapter.updateData(purchases)
        }
    }

    private fun getCurrentLoginId(): Int {
        val prefs = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        return prefs.getInt("loginId", -1)
    }
}