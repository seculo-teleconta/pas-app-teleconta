package com.teleconta.pas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.entities.Solicitation
import com.teleconta.pas.R

class SolicitationsAdapter(private val solicitations: List<Solicitation>) :
    RecyclerView.Adapter<SolicitationsAdapter.SolicitationsViewHolder>() {

    class SolicitationsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val idSolicitation: TextView = itemView.findViewById(R.id.idSolicitation)
        val operator: TextView = itemView.findViewById(R.id.solicitationOperator)
        val operatorId: TextView = itemView.findViewById(R.id.solicitationOperatorId)
        val type: TextView = itemView.findViewById(R.id.solicitationType)
        val date: TextView = itemView.findViewById(R.id.solicitationDate)
        val status: TextView = itemView.findViewById(R.id.solicitationStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitationsAdapter.SolicitationsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.solicitation_item, parent, false)
        return SolicitationsAdapter.SolicitationsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SolicitationsAdapter.SolicitationsViewHolder, position: Int) {
        val solicitation = solicitations[position]

        holder.idSolicitation.text = "${solicitation.id}"
        holder.operator.text = solicitation.operator
        holder.operatorId.text = "${solicitation.idOperator}"
        holder.type.text = solicitation.solicitationType
        holder.date.text = solicitation.solicitationDate
        holder.status.text = solicitation.solicitationStatus
    }

    override fun getItemCount(): Int {
        return solicitations.size
    }
}