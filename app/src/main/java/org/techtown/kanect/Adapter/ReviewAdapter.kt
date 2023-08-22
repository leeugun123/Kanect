package org.techtown.kanect.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.kanect.Data.UserIntel
import org.techtown.kanect.DetailActivity
import org.techtown.kanect.databinding.ReviewListLayoutBinding

class ReviewAdapter(private val reviewList : List<UserIntel>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {


    inner class ViewHolder(val binding : ReviewListLayoutBinding) : RecyclerView.ViewHolder(binding.root),
            View.OnClickListener{

                    init {
                        itemView.setOnClickListener(this)
                    }

                    override fun onClick(v: View?) {

                        val position = adapterPosition
                        val context = itemView.context

                    }


            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {

        val binding = ReviewListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {

        val userReview = reviewList[position]

        Glide.with(holder.itemView.context)
            .load(userReview.userImg)
            .circleCrop()
            .into(holder.binding.userImg)

        holder.binding.userName.text = userReview.userName
        holder.binding.userReview.text = userReview.userReview
        holder.binding.reviewTime.text = userReview.time



    }


    override fun getItemCount(): Int {
        return reviewList.size
    }




}