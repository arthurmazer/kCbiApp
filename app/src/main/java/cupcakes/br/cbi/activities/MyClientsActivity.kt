package cupcakes.br.cbi.activities

import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import cupcakes.br.cbi.R
import cupcakes.br.cbi.adapters.MyClientsAdapter
import cupcakes.br.cbi.commons.RecyclerScrollListener
import cupcakes.br.cbi.managers.MySqlHelper
import cupcakes.br.cbi.models.Client
import cupcakes.br.cbi.utils.Constants
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.activity_clients_constraint.*
import kotlinx.android.synthetic.main.content_clients.*
import org.jetbrains.anko.startActivity

/**
 * Created by monitorapc on 16-Oct-17.
 */

class MyClientsActivity() : AppCompatActivity(){

    val list_clients: ArrayList<Client> = arrayListOf<Client>()
    var recycler_clients: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_constraint)

        recycler_clients = recyclerView
        recycler_clients?.layoutManager = LinearLayoutManager(applicationContext)
        recycler_clients?.itemAnimator = DefaultItemAnimator()
        recycler_clients?.adapter = MyClientsAdapter(list_clients)
        recycler_clients?.addOnScrollListener(
                RecyclerScrollListener({onScrollUp()}, {onScrollDown()},recycler_clients?.layoutManager as LinearLayoutManager)
        )

        fab.setOnClickListener { view ->
            handleFabClick()
        }
    }

    fun onScrollUp(){
        fab.hide()
    }

    fun onScrollDown(){
        fab.show()
    }

    override fun onResume() {
        super.onResume()
        loadClients()
    }

    fun handleFabClick(){
        startActivity<AddClientActivity>()
    }

    fun loadClients(){
        var arrayClients: List<Client> = ArrayList<Client>()
        val db = MySqlHelper(applicationContext)

        arrayClients = db.selectAllClients()
        list_clients.clear()
        list_clients.addAll(arrayClients)
        recycler_clients?.adapter?.notifyDataSetChanged()
    }

}