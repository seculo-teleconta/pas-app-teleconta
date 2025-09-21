package com.teleconta.pas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.entities.Service
import com.teleconta.pas.R

class ServicesAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>(){

    class ServiceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val description: TextView = itemView.findViewById(R.id.serviceItemDescription)
        val value: TextView = itemView.findViewById(R.id.serviceItemValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.view_service_item, parent, false)
        return ServicesAdapter.ServiceViewHolder(item)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]

        holder.description.text = service.description
        holder.value.text = "R$${formatValue(service.value)}"
    }

    private fun formatValue(value: Double): String {
        return String.format("%.2f", value).replace('.', ',')
    }
}