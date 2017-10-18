package cupcakes.br.cbi.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.activity_clients.*

/**
 * Created by monitorapc on 16-Oct-17.
 */

class MyClientsActivity : AppCompatActivity(){

    fun MyClientsActivity(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)

        val cli = Client("asd", "01/09/1989", "(16) 99604-4224","www.facebook.com/arthurmazer", "12341515")
        //cli.saveThisClient(this.applicationContext)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }

}