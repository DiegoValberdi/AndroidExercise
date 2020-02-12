package com.diegovalberdi.androidexercise.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegovalberdi.androidexercise.data.remote.RetrofitFactory
import com.diegovalberdi.androidexercise.data.remote.RetrofitRemoteRepository
import com.diegovalberdi.androidexercise.model.User
import com.diegovalberdi.androidexercise.ui.userupdate.UserUpdateActivity
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import androidx.recyclerview.widget.RecyclerView
import com.diegovalberdi.androidexercise.R
import com.diegovalberdi.androidexercise.data.remote.RemoteRepository


class UserListFragment : Fragment(), UserListView {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var presenter: UserListPresenter
    private lateinit var txtFieldSearch: EditText
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var btnSearch: ImageButton

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        txtFieldSearch = view.findViewById(R.id.txtFieldSearch)
        usersRecyclerView = view.findViewById(R.id.usersRecyclerView)
        btnSearch = view.findViewById(R.id.btnSearch)

        val remoteRepository: RemoteRepository =
            RetrofitRemoteRepository(RetrofitFactory.getUserApi())

        presenter = UserListPresenter(
            this,
            remoteRepository
        )
        usersRecyclerView.layoutManager = LinearLayoutManager(activity)
        usersRecyclerView.setHasFixedSize(true)

        usersAdapter = UsersAdapter(
            presenter
        )
        usersRecyclerView.adapter = usersAdapter
        btnSearch.setOnClickListener {
            presenter.onSearchClicked(view.txtFieldSearch.text.toString().toIntOrNull())
        }
        presenter.init()

        return view
    }

    override fun showUsers(users: List<User>) {
        usersAdapter.addUsers(users)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.action_refresh) {
            presenter.showUsersList()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMessage(message: String) {
        val toastMessage = Toast.makeText(
            this.context,
            message, Toast.LENGTH_SHORT)
        toastMessage.show()
    }

    override fun openUserUpdate(user: User) {
        val intent = Intent(
            activity,
            UserUpdateActivity::class.java
        )
        intent.putExtra("user_id", user.id)
        intent.putExtra("user_name", user.name)
        intent.putExtra("user_birthdate", user.birthdate)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        presenter.init()
    }
}
