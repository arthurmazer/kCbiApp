package cupcakes.br.cbi.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.turingtechnologies.materialscrollbar.*
import cupcakes.br.cbi.R
import cupcakes.br.cbi.adapters.MyClientsAdapter
import cupcakes.br.cbi.commons.ClientInterface
import cupcakes.br.cbi.commons.RecyclerScrollListener
import cupcakes.br.cbi.managers.MySqlHelper
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.content_clients.*
import org.jetbrains.anko.startActivity


/**
 * Created by monitorapc on 16-Oct-17.
 */

class MyClientsActivity() : AppCompatActivity(), ClientInterface{


    val list_clients: ArrayList<Client> = arrayListOf<Client>()
    var recycler_clients: RecyclerView? = null
    var materialScrollbar: TouchScrollBar? = null
    var adapter_clients = MyClientsAdapter(list_clients)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_constraint)

        recycler_clients = recyclerView
        recycler_clients?.layoutManager = LinearLayoutManager(applicationContext)
        recycler_clients?.itemAnimator = DefaultItemAnimator()
        recycler_clients?.adapter = adapter_clients
        recycler_clients?.addOnScrollListener(
                RecyclerScrollListener({onScrollUp()}, {onScrollDown()},recycler_clients?.layoutManager as LinearLayoutManager)
        )

        var alphaInd = AlphabetIndicator(applicationContext)

        materialScrollbar = TouchScrollBar(this, recycler_clients, true)
        materialScrollbar?.setIndicator(
                alphaInd,
                true
        )


        fab.setOnClickListener { view ->
            handleFabClick()
        }


        searchView.setOnClickListener{
            searchView.setIconifiedByDefault(false)
        }

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter_clients.filter(query,this@MyClientsActivity)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter_clients.filter(newText, this@MyClientsActivity)
                return true
            }
        })


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
        var arrayClients: List<Client> = ArrayList<Client>() as List<Client>
        val db = MySqlHelper(applicationContext)

        arrayClients = db.selectAllClients()
        list_clients.clear()
        list_clients.addAll(arrayClients)
        recycler_clients?.adapter?.notifyDataSetChanged()
    }

    override fun onClientSearch(list_clients_searched: ArrayList<Client>) {
        list_clients.clear()
        list_clients.addAll(list_clients_searched)
        recycler_clients?.adapter?.notifyDataSetChanged()
    }

}