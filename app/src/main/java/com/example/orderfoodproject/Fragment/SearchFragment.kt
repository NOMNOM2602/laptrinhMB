package com.example.orderfoodproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderfoodproject.adapter.MenuAdapter
import com.example.orderfoodproject.databinding.FragmentSearchBinding
import com.example.orderfoodproject.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private  lateinit var adapter : MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val orignalMenuItems = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)


        setupSearchView()
        retrieveMenuItem()
        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodReference:DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        orignalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(orignalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem ,requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        val filteredMenuItems = orignalMenuItems.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
        }
    companion object {
    }
 }
