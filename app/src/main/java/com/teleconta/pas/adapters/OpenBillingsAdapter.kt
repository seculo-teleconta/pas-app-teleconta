package com.teleconta.pas.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.entities.OpenBilling

class OpenBillingsAdapter(private val billings: List<OpenBilling>) :
    RecyclerView.Adapter<OpenBillingsAdapter.OpenBillingViewHolder>() {

    class OpenBillingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valorTextView: TextView = itemView.findViewById(R.id.valor)
        val idTerminalTextView: TextView = itemView.findViewById(R.id.idTerminal)
        val dataVencTextView: TextView = itemView.findViewById(R.id.dataVenc)
        val statusTextView: TextView = itemView.findViewById(R.id.status)
        val code: TextView = itemView.findViewById(R.id.openBillingCode)
        val copyButton: Button = itemView.findViewById(R.id.openBillingCopyCodeButton)
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

        if(billing.code == null){
            holder.code.text = "Nenhum código ainda."
        }
        else {
            holder.code.text = billing.code
        }

        holder.copyButton.setOnClickListener {
            val clipboardManager =
                holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Code", billing.code)
            clipboardManager.setPrimaryClip(clip)

            // Show the message
            Toast.makeText(
                holder.itemView.context,
                "Code copied to clipboard",
                Toast.LENGTH_SHORT
            ).show()

            // Delay the removal of the message
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(
                    holder.itemView.context,
                    "", // Empty message to clear the previous toast
                    Toast.LENGTH_SHORT
                ).show()
            }, 4000)
        }
    }

    override fun getItemCount(): Int {
        return billings.size
    }
}
