package com.app.simplelogin.ui.main.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.app.simplelogin.R
import com.app.simplelogin.databinding.LayoutUserListItemBinding
import com.app.simplelogin.network.model.User
import com.app.simplelogin.ui.main.user.viewmodel.UserDetailsViewModel

class UsersListAdapter(private val items: List<User>, private val fm: FragmentManager) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(
        DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.layout_user_list_item,
                parent, false
            ), fm
    )

    override fun getItemCount(): Int {
        return if (items.isNullOrEmpty()) 0 else items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], position)
    }

    class ViewHolder(private var binding: LayoutUserListItemBinding, private val fm: FragmentManager) :
        RecyclerView.ViewHolder(binding.root) {
        var model: UserDetailsViewModel? = null
        fun bindItems(listItem: User, pos: Int) {
            model = UserDetailsViewModel()
            model?.childFragmentManager = fm
            model?.bind(listItem)
            binding.model = model
            binding.executePendingBindings()
            if(pos == 0) {
                model?.isTablet = binding.root.context.resources.getBoolean(R.bool.isTablet)
                model?.isTablet?.let {
                    if (it)
                        model?.navigateUserDetails(binding.root, listItem)
                }
            }
        }
    }
}