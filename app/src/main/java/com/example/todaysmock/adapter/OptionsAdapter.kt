package com.example.todaysmock.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaysmock.R
import com.example.todaysmock.databinding.AdapteroptionsBinding

class OptionsAdapter(val context: Context,val answerList:ArrayList<String>,
                     var onItemClicked: ((clickedItem: Int,String) -> Unit)):
    RecyclerView.Adapter<OptionsAdapter.QuestionsViewHolder>() {
    private var selectedPosition = -1

    inner class QuestionsViewHolder(var binding:AdapteroptionsBinding):
        RecyclerView.ViewHolder(binding.root){  }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val binding =AdapteroptionsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuestionsViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return answerList.size
    }
    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        with(holder.binding){
            tvoption.text=answerList[position]
            cvAnswer.setOnClickListener {
                onItemClicked(position,answerList[position])
                selectedPosition=position
                notifyDataSetChanged()
            }
            if(selectedPosition==position){
                cvAnswer.setCardBackgroundColor(context.getColor(R.color.selectedadapter))
                ivTick.visibility=View.VISIBLE
                selectedPosition=-1
            }else{
                holder.binding.cvAnswer.setCardBackgroundColor(context.getColor(R.color.white))
                ivTick.visibility=View.GONE
            }
        }

    }
}