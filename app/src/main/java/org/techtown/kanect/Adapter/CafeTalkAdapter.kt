package org.techtown.kanect.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.CafeChatInfo
import org.techtown.kanect.DetailActivity
import org.techtown.kanect.databinding.CafechatListLayoutBinding


class CafeTalkAdapter(private val cafeChatList : List<CafeChatInfo>) : RecyclerView.Adapter<CafeTalkAdapter.ViewHolder>(){


    inner class ViewHolder(val binding : CafechatListLayoutBinding ) : RecyclerView.ViewHolder(binding.root),
            View.OnClickListener{

                        init {
                            itemView.setOnClickListener(this)
                        }

                        override fun onClick(v: View?) {

                            val position = adapterPosition
                            val context = itemView.context


                        }



            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeTalkAdapter.ViewHolder {

        val binding = CafechatListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeTalkAdapter.ViewHolder, position: Int) {

        val cafeChatList = cafeChatList[position]

        Glide.with(holder.itemView.context)
            .load(cafeChatList.cafeImg)
            .fitCenter()
            .into(holder.binding.cafeImg)

        holder.binding.cafeName.text = cafeChatList.cafeName
        holder.binding.seatCount.text = cafeChatList.seat.toString()


        holder.binding.chatbut.setOnClickListener {

            Log.e("TAG","채팅화면 이동")

        }//버튼 누를 시 이동



    }

    override fun getItemCount(): Int {
        return cafeChatList.size
    }


}