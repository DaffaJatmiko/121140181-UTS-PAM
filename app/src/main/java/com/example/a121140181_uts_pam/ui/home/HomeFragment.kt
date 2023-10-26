package com.example.a121140181_uts_pam.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a121140181_uts_pam.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val dummyData = listOf(
        UserList("John Doe", "john@example.com"),
        UserList("Jane Doe", "jane@example.com"),
        UserList("Bob Smith", "bob@example.com"),
        UserList("Alice Johnson", "alice@example.com"),
        UserList("Charlie Brown", "charlie@example.com"),
        UserList("Emma Watson", "emma@example.com"),
        UserList("David Miller", "david@example.com"),
        UserList("Grace Kelly", "grace@example.com"),
        UserList("Michael Jordan", "michael@example.com"),
        UserList("Olivia Taylor", "olivia@example.com")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserAdapter(dummyData)
        recyclerView.adapter = adapter

        val searchView = binding.searchAction
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}