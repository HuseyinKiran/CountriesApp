package com.huseyinkiran.kotlincountriesnew.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huseyinkiran.kotlincountriesnew.databinding.FragmentCountryBinding
import com.huseyinkiran.kotlincountriesnew.databinding.FragmentFeedBinding
import com.huseyinkiran.kotlincountriesnew.util.downloadFromUrl
import com.huseyinkiran.kotlincountriesnew.util.placeholderProgressBar
import com.huseyinkiran.kotlincountriesnew.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid = 0
    private var binding : FragmentCountryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding= FragmentCountryBinding.inflate(layoutInflater) -> olsa da olmasa da sorun çözüldü ve ülkeler detayı yani fragmentcountry xml görünüyor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_country, container, false)
        binding = FragmentCountryBinding.inflate(inflater,container,false)
        return binding!!.root
       // return FragmentCountryBinding.inflate(inflater,container,false).root -> bunun yüzünden detaylarda veriler gelmiyordu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        //viewModel = ViewModelProvider(requireParentFragment())[CountryViewModel::class.java] // requireParentFragment() = requireActivity() = this
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                binding?.countryName?.text = country.countryName
                binding?.countryCapital?.text = country.countryCapital
                binding?.countryCurrency?.text = country.countryCurrency
                binding?.countryRegion?.text = country.countryRegion
                binding?.countryLanguage?.text = country.countryLanguage

                context?.let {
                    binding?.countryImage?.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
            }

        })
    }

}