package com.diegovalberdi.androidexercise.ui.useradd

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.diegovalberdi.androidexercise.R
import com.diegovalberdi.androidexercise.data.remote.RemoteRepository
import com.diegovalberdi.androidexercise.data.remote.RetrofitFactory
import com.diegovalberdi.androidexercise.data.remote.RetrofitRemoteRepository
import com.diegovalberdi.androidexercise.model.User
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class UserAddFragment : Fragment(), UserAddView {

    private lateinit var presenter: UserAddPresenter
    private lateinit var txtBirthdate: TextView
    private lateinit var btnAccept: Button
    private lateinit var btnCalendar: ImageButton
    private lateinit var txtFieldName: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        txtFieldName = view.findViewById(R.id.txtFieldName)
        txtBirthdate = view.findViewById(R.id.txtBirthdate)
        btnAccept = view.findViewById(R.id.btnAccept)
        btnCalendar = view.findViewById(R.id.btnCalendar)


        val remoteRepository: RemoteRepository =
            RetrofitRemoteRepository(RetrofitFactory.getUserApi())

        presenter = UserAddPresenter(
            this,
            remoteRepository
        )

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePicked = ""

        btnCalendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    datePicked = "${mMonth + 1}/${mDay}/${mYear}"
                    txtBirthdate.setText("Your birthdate is: " + datePicked)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        btnAccept.setOnClickListener {
            var newUserName = txtFieldName.text.toString()
            var allFieldsCompleted = true

            if (newUserName == "") {
                showMessage("Please introduce a username")
                allFieldsCompleted = false
            }
            if (datePicked == "") {
                showMessage("Please select a birthdate")
                allFieldsCompleted = false
            }
            if (allFieldsCompleted) {
                val user = User(0, newUserName, datePicked)
                presenter.addUser(user)
            }
        }

        return view
    }

    override fun showMessage(message: String) {
        val toastMessage = Toast.makeText(
            this.context,
            message, Toast.LENGTH_SHORT
        )
        toastMessage.show()
    }
}
