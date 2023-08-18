package org.techtown.kanect.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import org.techtown.kanect.Data.CafeIntel
import org.techtown.kanect.R
import org.techtown.kanect.databinding.CafeListLayoutBinding

class CafeListAdapter(private val cafeList : List<CafeIntel>) : RecyclerView.Adapter<CafeListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding : CafeListLayoutBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{

            init {
                itemView.setOnClickListener(this)
            }

        override fun onClick(v: View?) {

            val position = adapterPosition
            val context = itemView.context


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeListAdapter.ViewHolder {

        val binding = CafeListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CafeListAdapter.ViewHolder, position: Int) {

        val cafeList = cafeList[position]

        Glide.with(holder.itemView.context)
            .load(cafeList.cafeImg)
            .fitCenter()
            .into(holder.binding.cafeImg)

        holder.binding.cafeName.text = cafeList.cafeName
        holder.binding.seatCount.text = cafeList.seat

        if(cafeList.allHours){

            Glide.with(holder.itemView.context)
                .load(R.drawable.all_hour)
                .override(200,80)
                .into(holder.binding.allHourImg)
        }


        if(cafeList.opExist){
            holder.binding.operation.text = "영업중"
        }
        else{
            holder.binding.operation.text = "영업 종료"
        }


        if(cafeList.cur_seat > 15){
            holder.binding.curSeat.text = "HOT"
        }
        else
            holder.binding.curSeat.text = "FREE"

        if(cafeList.myCafe){

            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_baseline_thumb_up_blue)
                .override(100,100)
                .centerCrop()
                .into(holder.binding.mycafeBut)

        }
        else{

            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_baseline_thumb_up_24)
                .override(100,100)
                .centerCrop()
                .into(holder.binding.mycafeBut)


        }







    }

    override fun getItemCount(): Int {
        return cafeList.size
    }






}