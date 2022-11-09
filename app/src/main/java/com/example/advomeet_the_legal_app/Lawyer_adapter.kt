package com.example.advomeet_the_legal_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class Lawyer_adapter(private val Lawyerlist:ArrayList<Lawyer_database>) :
    RecyclerView.Adapter<Lawyer_adapter.MyViewHolder>() {

    private lateinit var mListner : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner : onItemClickListner){
        mListner=listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items,parent,false)
        return MyViewHolder(itemView,mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = Lawyerlist[position]
//        holder.titleImage.setImageResource(currentItem.image)
        holder.titleImage.setImageResource(R.drawable.lawyer)
        holder.name.text=currentItem.fname+" "+currentItem.lname
        holder.phoneno.text=currentItem.phoneno1
    }

    override fun getItemCount(): Int {
        return Lawyerlist.size
    }
    class MyViewHolder(itemView : View,listner : onItemClickListner):RecyclerView.ViewHolder(itemView){
        val titleImage : ShapeableImageView=itemView.findViewById(R.id.title_image)
        val name : TextView=itemView.findViewById(R.id.name)
        val phoneno : TextView=itemView.findViewById(R.id.Phoneno)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

}