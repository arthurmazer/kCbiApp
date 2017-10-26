package cupcakes.br.cbi.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.content_add_client.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class AddClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_add_client)

        val ctx = this.applicationContext

        btnCancel.onClick {

            val name = fName.text.toString()
            val birthday = fBirthday.text.toString()
            val facebook = fFacebook.text.toString()
            val location = ""
            val phoneNumber = fPhonenumber.text.toString()
            val cli = Client(name = name,birthday = birthday,phoneNumber = phoneNumber,facebookUrl = facebook,location = location)
            if ( checkFields() )
                cli.saveThisClient(ctx)
        }

        btnCancel.onClick {
            finish();
        }

    }

    fun checkFields(): Boolean{
        //todo check obligatory fields
        return true
    }




}
