package com.example.msggrid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.msggrid.databinding.LikedUserItemBinding

class GridAdapter(private val userList: List<User>) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LikedUserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            when (user.profileImage) {
                is ProfileImage.ResourceImage -> {
                    binding.profileImageView.setImageResource(user.profileImage.id)
                }
                is ProfileImage.DefaultImage -> {
                    binding.profileImageView.setImageResource(R.drawable.ic_profile_default)
                }

            }
            binding.userNameTextView.text = user.name

            when(user.isChecked){
                true -> binding.redNum1.isVisible = true
                false -> binding.redNum1.isVisible = false
            }
            binding.redNum1.visibility
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LikedUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}