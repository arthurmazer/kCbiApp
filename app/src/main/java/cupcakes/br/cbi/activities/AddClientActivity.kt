package cupcakes.br.cbi.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.content_add_client.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class AddClientActivity : AppCompatActivity() {

    var acPlace: PlaceAutocompleteFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_add_client)


        //acPlace = fragmentManager.get

        val ctx = this.applicationContext

        btnSave.onClick {

            val name = fName.text.toString()
            val birthday = fBirthday.text.toString()
            val location = "location"
            val phoneNumber = fPhonenumber.text.toString()
            val cli = Client(name = name,birthday = birthday,phoneNumber = phoneNumber)
            if ( checkFields() ) {
                cli.saveThisClient(ctx)
                finish()
            }
        }

    }

    fun checkFields(): Boolean{
        //todo check obligatory fields
        return true
    }




}
