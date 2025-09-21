package com.teleconta.pas.adapters

import android.view.LayoutInflater
import android.view.View
import com.teleconta.pas.R
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.entities.PhoneLine

class ChooseLinesAdapter(private val lines: List<PhoneLine>, private val callback: ChooseLinesCallback) :
    RecyclerView.Adapter<ChooseLinesAdapter.ChooseLineViewHolder>(){

    class ChooseLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val lineTextView: TextView = itemView.findViewById(R.id.lineToChooseLine)
        val operatorTextView: TextView = itemView.findViewById(R.id.lineToChooseOperator)
        val selectButton: Button = itemView.findViewById(R.id.lineToChooseSelectButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseLineViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.line_to_choose, parent, false)

        return ChooseLineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChooseLineViewHolder, position: Int) {
        val line = lines[position]

        holder.lineTextView.text = line.idTerminal
        holder.operatorTextView.text = line.operator

        holder.selectButton.setOnClickListener {
            holder.selectButton.isEnabled = false

            callback.onSelectLine(line.idTerminal, line.idOperator)

            holder.selectButton.postDelayed({
                holder.selectButton.isEnabled = true
            }, 1500)
        }
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    interface ChooseLinesCallback{
        fun onSelectLine(line: String, idOperator: Long)
    }
}