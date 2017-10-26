package cupcakes.br.cbi.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.content_clients.*
import org.jetbrains.anko.startActivity

/**
 * Created by monitorapc on 16-Oct-17.
 */

class MyClientsActivity : AppCompatActivity(){

    fun MyClientsActivity(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
        

        fab.setOnClickListener { view ->
            handleFabClick()
        }

    }

    fun handleFabClick(){
        startActivity<AddClientActivity>()
    }

}