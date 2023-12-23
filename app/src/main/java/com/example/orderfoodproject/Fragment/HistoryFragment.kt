package com.example.orderfoodproject.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderfoodproject.R
import com.example.orderfoodproject.adapter.BuyAgainAdapter
import com.example.orderfoodproject.databinding.FragmentHistoryBinding



class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
    private fun setUpRecyclerView()
    {
        val buyAgainFoodName = arrayListOf("Donut","Beefsteak","Pizza")
        val buyAgainFoodPrice = arrayListOf("5$","53$","44$")
        val buyAgianFoodImgae = arrayListOf(
            R.drawable.donut,
            R.drawable.beefsteak,
            R.drawable.pizza)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgianFoodImgae)
        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    companion object{

    }
}


