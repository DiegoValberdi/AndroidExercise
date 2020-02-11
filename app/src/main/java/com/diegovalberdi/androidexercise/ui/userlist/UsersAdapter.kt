package com.diegovalberdi.androidexercise.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diegovalberdi.androidexercise.R
import com.diegovalberdi.androidexercise.model.User

class UsersAdapter(private val presenter: UserListPresenter) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var users = listOf<User>()

    fun addUsers(newUsers: List<User>) {
        this.users = newUsers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position],presenter)
    }

    class ViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val txtId = view.findViewById<TextView>(R.id.txtId)
        private val txtBirthdate: TextView = view.findViewById(R.id.txtBirthdate)
        private val txtName: TextView = view.findViewById(R.id.txtName)
        private val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)


        fun bind(user: User,presenter: UserListPresenter) {
            txtId.text = user.id.toString()
            txtBirthdate.text = user.birthdate
            txtName.text = user.name
            btnDelete.setOnClickListener{presenter.onDeleteClicked(user.id)}
            this.itemView.setOnClickListener { presenter.onUserClicked(user)}
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
                return ViewHolder(view)
            }
        }
    }

}