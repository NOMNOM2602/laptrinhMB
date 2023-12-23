package com.example.orderfoodproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.orderfoodproject.MenuBootomSheetFragment
import com.example.orderfoodproject.R
import com.example.orderfoodproject.adapter.MenuAdapter
import com.example.orderfoodproject.adapter.PopularApdapter
import com.example.orderfoodproject.databinding.FragmentHomeBinding
import com.example.orderfoodproject.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems :MutableList<MenuItem>
    private lateinit var binding:FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialogFragment = MenuBootomSheetFragment()
            bottomSheetDialogFragment.show(parentFragmentManager,"Test")
        }
        retrieveAndDisplayPopulerItem()
        return binding.root

    }

    private fun retrieveAndDisplayPopulerItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                randomPopulerItems()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun randomPopulerItems() {
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subMenuItems = index.take(numItemToShow).map { menuItems[it] }

        setPopulerItemAdapter(subMenuItems)
    }

    private fun setPopulerItemAdapter(subMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subMenuItems,requireContext())
        binding.PopularRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecycleView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
    }
