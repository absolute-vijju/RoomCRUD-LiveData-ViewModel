package com.developer.vijay.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.room.database.UserEntity
import com.developer.vijay.room.roomcrud.databinding.FragmentFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment() {

    private val roomViewModel: RoomViewModel by viewModels()
    private lateinit var mBinding: FragmentFavouriteBinding
    private val usersAdapter by lazy {
        UsersAdapter { position, itemId ->
            val currentUser = userList[position]
            roomViewModel.addRemoveFavourite(currentUser.userId!!, !currentUser.isFavourite)
        }
    }
    private var userList = listOf<UserEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFavouriteBinding.inflate(inflater)
        return mBinding.root
    }

    override fun init() {
        activity?.let {
            val itemTouchHelper =
                ItemTouchHelper(SwipeToDeleteHelper(object : SwipeToDeleteHelper.SwipeToDelete {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        roomViewModel?.deleteUser(userList!![position])
                    }
                }))
            mBinding.rvRoomDashboard.layoutManager = LinearLayoutManager(it)
            mBinding.rvRoomDashboard.setHasFixedSize(true)
            itemTouchHelper.attachToRecyclerView(mBinding.rvRoomDashboard)
            mBinding.rvRoomDashboard.adapter = usersAdapter
        }
        roomViewModel.getFavouriteUsers().observe(viewLifecycleOwner) {
            userList = it
            usersAdapter.changeAdapterData(it)
        }
    }

    override fun setListener() {

    }
}