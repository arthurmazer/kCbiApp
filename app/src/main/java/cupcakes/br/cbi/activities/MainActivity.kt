package cupcakes.br.cbi.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cupcakes.br.cbi.R
import kotlinx.android.synthetic.main.activity_main.*;
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClients.setOnClickListener {
            startActivity<MyClientsActivity>()
        }
    }
}
