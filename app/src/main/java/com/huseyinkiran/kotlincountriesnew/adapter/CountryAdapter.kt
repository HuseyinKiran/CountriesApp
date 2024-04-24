package com.huseyinkiran.kotlincountriesnew.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.huseyinkiran.kotlincountriesnew.R
import com.huseyinkiran.kotlincountriesnew.databinding.ItemCountryBinding
import com.huseyinkiran.kotlincountriesnew.model.Country
import com.huseyinkiran.kotlincountriesnew.util.downloadFromUrl
import com.huseyinkiran.kotlincountriesnew.util.placeholderProgressBar
import com.huseyinkiran.kotlincountriesnew.view.FeedFragmentDirections


class CountryAdapter(private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    //private var binding : ItemCountryBinding? = null

    class CountryViewHolder(var binding : ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)) //-> DataBindingUtil olmadan çalışan buydu

        /* val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(binding)

           val view = DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.from(parent.context),
            R.layout.item_country,parent,false)
        return CountryViewHolder(view)

         */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
       /* with(holder){
            with(countryList[position]){
                binding.name.text = countryName
                binding.region.text=countryRegion
            }  pek önemi yok aslında ama dursun
        } */

       // holder.binding.country = countryList[position] // aşağıdaki 2 satırı karşılıyor DataBinding ile
       // holder.view.listener=this

        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.binding.layoutItemCountry.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context))



     /*   holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

       holder.binding.layoutItemCountry.setOnClickListener {
           val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
           Navigation.findNavController(it).navigate(action)
       }

        // xml'den imageView içine android:downloadUrl:"@{country.imageUrl}" yazarak aşağıdaki kodun karşılığını aldık
        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context))  */

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


}

