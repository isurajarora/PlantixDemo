package net.plantix.demo.view.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.plantix.demo.R
import net.plantix.demo.data.model.ModelUserName
import net.plantix.demo.util.extensions.toTitleCase

class UserNameAdapter(private val context: Context, private var UserNameList: ArrayList<ModelUserName> ):
    RecyclerView.Adapter<UserNameAdapter.UserNameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNameViewHolder {
        return UserNameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_rv_main,parent,false))
    }

    override fun onBindViewHolder(holder: UserNameViewHolder, position: Int) {
        val User = UserNameList[position]
        holder.name.text = String.Companion.toTitleCase(User.UserName)
    }

    override fun getItemCount(): Int = UserNameList.size

    class UserNameViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tv_rvrow_title)
    }

    fun SetData(UserList: ArrayList<ModelUserName>){
        this.UserNameList = UserList
        notifyDataSetChanged()
    }
}