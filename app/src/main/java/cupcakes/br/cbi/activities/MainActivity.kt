package cupcakes.br.cbi.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cupcakes.br.cbi.R
import cupcakes.br.cbi.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClients.setOnClickListener {
            startActivity<MyClientsActivity>()
        }

        btnMenu.setOnClickListener({
            startActivity<MyMenuActivity>()
        })
    }
}
