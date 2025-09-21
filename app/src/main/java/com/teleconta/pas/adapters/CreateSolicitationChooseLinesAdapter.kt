package com.teleconta.pas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.entities.PhoneLine

class CreateSolicitationChooseLinesAdapter(private val lines: List<PhoneLine>, private val callback: CreateSolicitationChooseLinesAdapter.ChooseLinesCallback) :
    RecyclerView.Adapter<CreateSolicitationChooseLinesAdapter.ChooseLineViewHolder>(){

    class ChooseLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val lineTextView: TextView = itemView.findViewById(R.id.lineToChooseLine2)
        val operatorTextView: TextView = itemView.findViewById(R.id.lineToChooseOperator2)
        val selectButton: Button = itemView.findViewById(R.id.lineToChooseSelectButton2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateSolicitationChooseLinesAdapter.ChooseLineViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.create_solicitation_line_item, parent, false)

        return ChooseLineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CreateSolicitationChooseLinesAdapter.ChooseLineViewHolder, position: Int) {
        val line = lines[position]

        holder.lineTextView.text = line.idTerminal
        holder.operatorTextView.text = line.operator

        holder.selectButton.setOnClickListener {
            holder.selectButton.isEnabled = false

            callback.onSelectLine(line.idTerminal, line.idOperator)
        }
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    interface ChooseLinesCallback{
        fun onSelectLine(line: String, idOperator: Long)
    }
}