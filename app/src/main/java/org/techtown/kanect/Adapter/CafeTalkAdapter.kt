package org.techtown.kanect.Adapter

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.ChatActivity
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

    override fun onBindViewHolder(holder : CafeTalkAdapter.ViewHolder, position: Int) {

        val cafeChatList = cafeChatList[position]

        Glide.with(holder.itemView.context)
            .load(cafeChatList.cafeImg)
            .fitCenter()
            .into(holder.binding.cafeImg)

        holder.binding.cafeName.text = cafeChatList.cafeName
        holder.binding.seatCount.text = cafeChatList.seat.toString()


        holder.binding.chatbut.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)

            alertDialogBuilder.setTitle("채팅방 입장")
            alertDialogBuilder.setMessage(cafeChatList.cafeName + " 채팅방에 입장하시겠습니까?")

            // "예" 버튼 설정 및 클릭 리스너 추가
            alertDialogBuilder.setPositiveButton("예") { dialog, which ->

                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                intent.putExtra("cafeName", cafeChatList.cafeName)
                intent.putExtra("cafeImg", cafeChatList.cafeImg)
                holder.itemView.context.startActivity(intent)

            }

            alertDialogBuilder.setNegativeButton("아니요") { dialog, which ->

            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()


        }//버튼 누를 시 이동



    }

    override fun getItemCount(): Int {
        return cafeChatList.size
    }


}