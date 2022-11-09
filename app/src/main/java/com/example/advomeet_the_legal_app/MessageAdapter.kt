package com.example.advomeet_the_legal_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advomeet_the_legal_app.databinding.RecieverLayoutItemBinding
import com.example.advomeet_the_legal_app.databinding.SentItemlayoutBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context:Context,var list:ArrayList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var Item_Sent=1
    var Item_recieved=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==Item_Sent)
            SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_itemlayout,parent,false))
        else RecieverViewHolder(LayoutInflater.from(context).inflate(R.layout.reciever_layout_item,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        // how to find value from table
        return if (FirebaseAuth.getInstance().uid == list[position].senderid) Item_Sent else Item_recieved
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]

        if(holder.itemViewType==Item_Sent){
            val viewHolder=holder as SentViewHolder
            viewHolder.binding.usermessage.text=message.message
        }
        else{
            val viewHolder=holder as RecieverViewHolder
            viewHolder.binding.usermessage.text=message.message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class SentViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding=SentItemlayoutBinding.bind(view)
    }
    inner class RecieverViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding=RecieverLayoutItemBinding.bind(view)
    }
}