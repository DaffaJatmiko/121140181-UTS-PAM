package com.example.a121140181_uts_pam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a121140181_uts_pam.databinding.ListItemBinding

class UserAdapter(private val userList: List<UserList>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {

    private lateinit var binding: ListItemBinding
    private var filteredUserList: List<UserList> = userList

    class UserViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userName: TextView = binding.titleImage
        val userEmail: TextView = binding.textEmail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = filteredUserList[position]
        holder.userName.text = user.userName
        holder.userEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return filteredUserList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    userList
                } else {
                    userList.filter { user ->
                        user.userName.contains(constraint, true) ||
                                user.email.contains(constraint, true)
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredUserList = results?.values as? List<UserList> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}