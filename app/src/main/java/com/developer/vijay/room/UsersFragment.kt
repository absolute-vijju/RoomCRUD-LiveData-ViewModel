package com.developer.vijay.room

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.room.database.UserEntity
import com.developer.vijay.room.roomcrud.*
import com.developer.vijay.room.roomcrud.databinding.DialogRoomBinding
import com.developer.vijay.room.roomcrud.databinding.FragmentUsersBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment(), View.OnClickListener {

    private val roomViewModel: RoomViewModel by viewModels()
    private lateinit var mBinding: FragmentUsersBinding
    private var userList = listOf<UserEntity>()
    private val dialogRoomBinding: DialogRoomBinding? = null

    private val usersAdapter by lazy {
        UsersAdapter { position, itemId ->
            val currentUser = userList[position]
            when (itemId) {
                R.id.ivFavourite -> {
                    roomViewModel.addRemoveFavourite(currentUser.userId!!, !currentUser.isFavourite)
                }
                else -> {
                    showDialog(currentUser)
                }
            }
        }
    }
    private var pickImageContract: ActivityResultLauncher<String>? = null
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentUsersBinding.inflate(inflater)
        return mBinding.root
    }

    override fun init() {

        pickImageContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, it)
                dialogRoomBinding?.apply { ivProfile.setImageBitmap(bitmap) }
            }
        }

        activity?.let {
            val itemTouchHelper =
                ItemTouchHelper(SwipeToDeleteHelper(object : SwipeToDeleteHelper.SwipeToDelete {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        roomViewModel.deleteUser(userList[position])
                    }
                }))
            mBinding.rvRoomDashboard.layoutManager = LinearLayoutManager(it)
            mBinding.rvRoomDashboard.setHasFixedSize(true)
            itemTouchHelper.attachToRecyclerView(mBinding.rvRoomDashboard)
            mBinding.rvRoomDashboard.adapter = usersAdapter
        }

        roomViewModel.getUsers().observe(viewLifecycleOwner) {
            userList = it
            usersAdapter.changeAdapterData(it)
        }
    }

    override fun setListener() {
        mBinding.fabAddUser.setOnClickListener(this)
    }

    private fun showDialog(userEntity: UserEntity? = null) {
        var isFavourite = false
        val dialogRoomBinding = DialogRoomBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogRoomBinding.root)

        if (isFavourite)
            dialogRoomBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_selected))
        else
            dialogRoomBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_unselected))

        dialogRoomBinding.ivProfile.setOnClickListener { pickImageContract?.launch("image/*") }

        dialogRoomBinding.ivFavourite.setOnClickListener {
            isFavourite = !isFavourite
            if (isFavourite)
                dialogRoomBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_selected))
            else
                dialogRoomBinding.ivFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_unselected))
        }

        userEntity?.let {
            bitmap = it.image
            isFavourite = it.isFavourite
            dialogRoomBinding.ivProfile.setImageBitmap(it.image)
            dialogRoomBinding.etFullName.setText(it.name)
            dialogRoomBinding.etNumber.setText(it.number)
        }

        dialogRoomBinding.btnSubmit.setOnClickListener {

            if (bitmap == null) {
                activity?.showShortToast("Please select your image.")
                return@setOnClickListener
            }

            dialog.dismiss()
            activity?.let {
                Log.d("mydata", Gson().toJson(userEntity))
                if (userEntity != null) {
                    userEntity.apply {
                        name = dialogRoomBinding.etFullName.text.toString()
                        number = dialogRoomBinding.etNumber.text.toString()
                        isFavourite = isFavourite
                        image = bitmap!!
                    }
                    roomViewModel.updateUser(userEntity)
                    context?.showShortToast("User UPDATED")
                } else {
                    roomViewModel.insertUser(
                        UserEntity(dialogRoomBinding.etFullName.text.toString(), dialogRoomBinding.etNumber.text.toString(), isFavourite, bitmap!!)
                    )
                    context?.showShortToast("User ADDED.")
                }
            }
        }
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fabAddUser -> {
                showDialog()
            }
        }
    }
}