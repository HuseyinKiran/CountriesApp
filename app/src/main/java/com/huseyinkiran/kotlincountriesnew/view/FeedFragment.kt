package com.huseyinkiran.kotlincountriesnew.view

import androidx.fragment.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.kotlincountriesnew.adapter.CountryAdapter
import com.huseyinkiran.kotlincountriesnew.databinding.FragmentFeedBinding
import com.huseyinkiran.kotlincountriesnew.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

     private var binding: FragmentFeedBinding? = null // fragment binding ekleme
    // it's the last solution for id 'kotlin-android-extensions' failure
     private lateinit var viewModel: FeedViewModel
     private val countryAdapter = CountryAdapter(arrayListOf())


    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = FragmentFeedBinding.inflate(layoutInflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    @Deprecated("Deprecated in Java")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding?.countryList?.layoutManager = LinearLayoutManager(context)
        binding?.countryList?.adapter = countryAdapter

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            binding?.countryList?.visibility = View.GONE
            binding?.countryError?.visibility = View.GONE
            binding?.countryLoading?.visibility = View.GONE
            viewModel.refreshFromAPI()
            binding?.swipeRefreshLayout?.isRefreshing = false

        }

        observeLiveData()


    }

    private fun observeLiveData(){

        viewModel.countries.observe(viewLifecycleOwner, Observer {countries->

            countries?.let {
                binding?.countryList?.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                   binding?.countryError?.visibility = View.VISIBLE
                }else{
                   binding?.countryError?.visibility = View.GONE
                }
            }

        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if (it){
                   binding?.countryLoading?.visibility = View.VISIBLE
                    binding?.countryList?.visibility = View.GONE
                   binding?.countryError?.visibility = View.GONE
                }else{
                   binding?.countryLoading?.visibility = View.GONE
                }
            }
        })

    }

}