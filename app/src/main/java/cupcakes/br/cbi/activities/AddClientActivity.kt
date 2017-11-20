package cupcakes.br.cbi.activities

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.content_add_client.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import android.provider.ContactsContract
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import cupcakes.br.cbi.utils.Constants
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class AddClientActivity : AppCompatActivity() {

    private val READ_CONTACTS_PERMISSIONS_REQUEST = 1
    var clientID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_add_client)

        //check if is edit
        var edit = intent.getBooleanExtra(Constants.EDIT_CLIENT, false)

        //this statement is only for adding new clients
        if (!edit) {
            alert(resources.getString(R.string.alert_message)) {
                title = resources.getString(R.string.alert_title)
                positiveButton(resources.getString(R.string.alert_positive_message)) { getPermissionAndCallReadUserContacts() }
                negativeButton(resources.getString(R.string.alert_negative_message)) { }
            }.show()

        }else{
            //get extras for edit
            fName.setText(intent.getStringExtra(Constants.EDIT_CLIENT_NAME))
            fBirthday.setText(intent.getStringExtra(Constants.EDIT_CLIENT_BIRTHDAY))
            fPhonenumber.setText(intent.getStringExtra(Constants.EDIT_CLIENT_PHONE))
            clientID = intent.getStringExtra(Constants.EDIT_CLIENT_ID)
        }

        val ctx = this.applicationContext

        btnSave.onClick {

            val name = fName.text.toString()
            val birthday = fBirthday.text.toString()
            val phoneNumber = fPhonenumber.text.toString()
            val cli = Client(name = name,birthday = birthday,phoneNumber = phoneNumber)
            if ( checkFields() ) {

                if (edit){
                    if (clientID.isNotEmpty()){
                        try{
                            cli.updateThisClient(ctx,clientID.toInt())
                            finish()
                        }catch (exception: ClassCastException){
                            toast(resources.getString(R.string.save_client_error_message))
                        }
                    }else{
                        toast(resources.getString(R.string.save_client_error_message))
                    }

                }else{
                    cli.saveThisClient(ctx)

                    alert(resources.getString(R.string.save_client_success_message)) {
                        title = resources.getString(R.string.save_client_success_title)
                        positiveButton(resources.getString(R.string.yes)) {
                            cleanFields(); getPermissionAndCallReadUserContacts()  }
                        negativeButton(resources.getString(R.string.no)) { finish() }
                    }.show()
                }

            }
        }

        fBirthday.onClick {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this@AddClientActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                //Toast("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
                var month = "" + (monthOfYear+1)
                var day = "" + dayOfMonth
                if (monthOfYear < 10)
                    month = "0" + monthOfYear
                if (dayOfMonth < 10)
                    day = "0" + dayOfMonth

                var date = "$day/$month/$year"
                fBirthday.setText(date)
            }, year, month, day)
            dpd.show()
        }


    }

    fun cleanFields(){
        fName.setText("")
        fBirthday.setText("")
        fPhonenumber.setText("")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            when (requestCode) {
                1 -> if (resultCode == Activity.RESULT_OK) {
                    val contactData = data?.data

                    val cur = contentResolver.query(contactData!!, null, null, null, null)
                    if (cur!!.count > 0) {// thats mean some resutl has been found
                        if (cur.moveToNext()) {
                            val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                            val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            fName.setText(name)

                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                                val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                                while (phones!!.moveToNext()) {
                                    val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    fPhonenumber.setText(phoneNumber)
                                }
                                phones.close()
                            }

                        }
                    }
                    cur.close()
                }
            }

    }

    fun checkFields(): Boolean{
        val name = fName.text.toString()
        val phoneNumber = fPhonenumber.text.toString()
        val message = resources.getString(R.string.error_missing_fields)
        var missingFields = false

        if ( name.isEmpty() ){
           fName.setBackgroundResource(R.drawable.custom_edittext_error)
            missingFields = true
        }else{
            fName.setBackgroundResource(R.drawable.custom_edittext)
        }

        if ( phoneNumber.isEmpty() ){
            fPhonenumber.setBackgroundResource(R.drawable.custom_edittext_error)
            missingFields = true
        }else{
            fPhonenumber.setBackgroundResource(R.drawable.custom_edittext)
        }

        if (missingFields)
            toast(message)

        return !(name.isEmpty() || phoneNumber.isEmpty())

    }




    fun getPermissionAndCallReadUserContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_CONTACTS), READ_CONTACTS_PERMISSIONS_REQUEST)
            //requestPermissions()
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                callContactsList()
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callContactsList()
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)

                if (showRationale) {
                    // do something here to handle degraded mode
                } else {
                    Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun callContactsList(){
        val contactPickerIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(contactPickerIntent, 1)
    }



}
