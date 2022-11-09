package com.example.advomeet_the_legal_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class Client_adapter(private val Clientlist:ArrayList<Client_data>):
    RecyclerView.Adapter<Client_adapter.MyViewHolder>() {

    private lateinit var mListner : Client_adapter.onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner : Client_adapter.onItemClickListner){
        mListner=listner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Client_adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items,parent,false)
        return Client_adapter.MyViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: Client_adapter.MyViewHolder, position: Int) {
        val currentItem = Clientlist[position]
//        holder.titleImage.setImageResource(currentItem.image)
        holder.titleImage.setImageResource(R.drawable.lawyer)
        holder.name.text=currentItem.fname+" "+currentItem.lname
        holder.phoneno.text=currentItem.phoneno1
    }

    override fun getItemCount(): Int {
        return Clientlist.size
    }
    class MyViewHolder(itemView : View,listner : Client_adapter.onItemClickListner):RecyclerView.ViewHolder(itemView){
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