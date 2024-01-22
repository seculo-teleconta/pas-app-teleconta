package com.example.firstapp_tutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp_tutorial.R
import com.example.firstapp_tutorial.entities.OpenBilling
import java.text.SimpleDateFormat
import java.util.Locale

class OpenBillingsAdapter(private val billings: List<OpenBilling>) :
    RecyclerView.Adapter<OpenBillingsAdapter.OpenBillingViewHolder>() {

    class OpenBillingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valorTextView: TextView = itemView.findViewById(R.id.valor)
        val idTerminalTextView: TextView = itemView.findViewById(R.id.idTerminal)
        val dataVencTextView: TextView = itemView.findViewById(R.id.dataVenc)
        val statusTextView: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenBillingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.open_billing_item, parent, false)
        return OpenBillingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpenBillingViewHolder, position: Int) {
        val billing = billings[position]

        holder.valorTextView.text = "R$ ${billing.value}"
        holder.idTerminalTextView.text = "${billing.idTerminal}"
        holder.dataVencTextView.text = "${billing.dateVenc}"
        holder.statusTextView.text = "${billing.status}"
    }

    override fun getItemCount(): Int {
        return billings.size
    }
}
