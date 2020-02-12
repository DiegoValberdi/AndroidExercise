package com.diegovalberdi.androidexercise.ui.userupdate

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import com.diegovalberdi.androidexercise.R
import com.diegovalberdi.androidexercise.data.remote.RemoteRepository
import com.diegovalberdi.androidexercise.data.remote.RetrofitFactory
import com.diegovalberdi.androidexercise.data.remote.RetrofitRemoteRepository
import com.diegovalberdi.androidexercise.model.User
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class UserUpdateActivity : AppCompatActivity(), UserUpdateView {

    private lateinit var txtBirthdate: TextView
    private lateinit var txtViewTitle: TextView
    private lateinit var btnAccept: Button
    private lateinit var btnCalendar: ImageButton
    private lateinit var txtFieldName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)


        txtFieldName = findViewById(R.id.txtFieldName)
        txtBirthdate = findViewById(R.id.txtBirthdate)
        txtViewTitle = findViewById(R.id.txtViewTitle)
        btnAccept = findViewById(R.id.btnAccept)
        btnCalendar = findViewById(R.id.btnCalendar)

        val userId = intent.extras?.getInt("user_id")!!
        val userName = intent.extras?.getString("user_name")!!
        val userBirthdate = intent.extras?.getString("user_birthdate")!!

        val remoteRepository: RemoteRepository =
            RetrofitRemoteRepository(RetrofitFactory.getUserApi())

        val presenter = UserUpdatePresenter(this, remoteRepository)


        txtViewTitle.setText("Update ${userName}")

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePicked = userBirthdate

        btnCalendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    datePicked = "${mMonth+1}/${mDay}/${mYear}"
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

            if (newUserName == "") {
                newUserName = userName
            }
            val user = User(userId, newUserName, datePicked)
            presenter.updateUser(user)
            finish()
        }

    }

    override fun showMessage(message: String) {

        val toastMessage = Toast.makeText(
            applicationContext,
            message, Toast.LENGTH_SHORT
        )
        toastMessage.show()
    }

}
