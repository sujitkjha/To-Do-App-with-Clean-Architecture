package com.sujitjha.todo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sujitjha.data.models.Priority
import com.sujitjha.data.models.ToDoData
import com.sujitjha.todo.R
import com.sujitjha.todo.databinding.RowLayoutBinding


class ListAdapter :RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    private lateinit var binding :RowLayoutBinding

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         binding =RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        binding.titleTxt.text= dataList[position].title
        binding.descriptionTxt.text = dataList[position].description

        val priority = dataList[position].priority

        binding.rowBackground.setOnClickListener{
           val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)

        }

        when(dataList[position].priority){
            Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.red
            ))

            Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.yello
            ))

            Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.green
            ))

        }



    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList =toDoData
        notifyDataSetChanged()

    }
}