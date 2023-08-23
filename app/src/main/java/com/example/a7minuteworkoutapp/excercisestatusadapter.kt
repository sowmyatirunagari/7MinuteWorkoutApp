package com.example.a7minuteworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.ItemExcerciseStatusBinding

class Excercisestatusadapter(val items:ArrayList<excercise>):
    RecyclerView.Adapter<Excercisestatusadapter.ViewHolder>(){

        class ViewHolder(binding:ItemExcerciseStatusBinding):
            RecyclerView.ViewHolder(binding.root){
            val tvItem=binding.tvItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemExcerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:excercise=items[position]
        holder.tvItem.text=model.getId().toString()


        when {
            model.getIsSelected() ->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.circular_thin_color_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
            model.getIsCompleted()  ->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.circluarshapeborder)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))

            }
            else->{
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.itemcircularcolargreyback)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))


            }
        }
    }

    override fun getItemCount(): Int {
        return items.size

    }
}