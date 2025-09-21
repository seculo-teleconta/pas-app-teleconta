package com.teleconta.pas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.entities.SolicitationType

class SolicitationsTypesAdapter (private val types: List<SolicitationType>, private val callback: SolicitationsTypesAdapter.ChooseTypeCallback) :
    RecyclerView.Adapter<SolicitationsTypesAdapter.ChooseTypesViewHolder>(){

    class ChooseTypesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val typeName: TextView = itemView.findViewById(R.id.solicitationsTypeNameItem)
        val selectButton: Button = itemView.findViewById(R.id.selectSolicitationType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitationsTypesAdapter.ChooseTypesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.create_solicitation_type_item, parent, false)

        return ChooseTypesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SolicitationsTypesAdapter.ChooseTypesViewHolder, position: Int) {
        val type = types[position]

        holder.typeName.text = type.name

        holder.selectButton.setOnClickListener {
            holder.selectButton.isEnabled = false

            callback.onSelectLine(type.id)
        }
    }

    override fun getItemCount(): Int {
        return types.size
    }

    interface ChooseTypeCallback{
        fun onSelectLine(type: Long)
    }
}