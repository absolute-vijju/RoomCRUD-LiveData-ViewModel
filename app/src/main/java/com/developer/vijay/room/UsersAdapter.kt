package com.developer.vijay.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.room.roomcrud.R
import com.developer.vijay.room.roomcrud.databinding.ItemUserBinding
import com.developer.vijay.room.database.UserEntity

class UsersAdapter(val itemClickListener: (position: Int, itemId: Int?) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.HomeViewHolder>() {

    private var usersList = listOf<UserEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemUserBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class HomeViewHolder(private val mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bindData() {

            usersList[adapterPosition]?.apply {
                mBinding.tvName.text = name
                mBinding.tvNumber.text = number
                mBinding.ivProfile.setImageBitmap(image)
                if (isFavourite)
                    mBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(this@HomeViewHolder.mBinding.root.context, R.drawable.ic_baseline_star_selected))
                else
                    mBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(this@HomeViewHolder.mBinding.root.context, R.drawable.ic_baseline_star_unselected))
            }

            mBinding.root.setOnClickListener {
                itemClickListener(adapterPosition, null)
            }

            mBinding.ivFavourite.setOnClickListener {
                itemClickListener(adapterPosition, R.id.ivFavourite)
            }
        }
    }

    fun changeAdapterData(usersList: List<UserEntity>) {
        this.usersList = usersList
        notifyDataSetChanged()
    }
}