package org.techtown.kanect.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.DailyAuth
import org.techtown.kanect.databinding.DailyAuthBinding

class DailyAuthAdapter (private val authList : List<DailyAuth>) : RecyclerView.Adapter<DailyAuthAdapter.ViewHolder>() {


    inner class ViewHolder(val binding : DailyAuthBinding) : RecyclerView.ViewHolder(binding.root),
            View.OnClickListener{


                init{
                    itemView.setOnClickListener(this)
                }

                override fun onClick(v: View?) {

                    val position = adapterPosition
                    val context = itemView.context

                }

            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAuthAdapter.ViewHolder {

        val binding = DailyAuthBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DailyAuthAdapter.ViewHolder, position: Int) {

        val userAuth = authList[position]

        Glide.with(holder.itemView.context)
            .load(userAuth.userImg)
            .circleCrop()
            .into(holder.binding.userImg)

        holder.binding.userName.text = userAuth.userName

        Glide.with(holder.itemView.context)
            .load(userAuth.authImg)
            .fitCenter()
            .into(holder.binding.authImg)

        holder.binding.authText.text = userAuth.authText
        holder.binding.postDate.text = userAuth.postDate

    }

    override fun getItemCount(): Int {
        return authList.size
    }



}


