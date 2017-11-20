package cupcakes.br.cbi.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.turingtechnologies.materialscrollbar.INameableAdapter
import cupcakes.br.cbi.R
import cupcakes.br.cbi.activities.AddClientActivity
import cupcakes.br.cbi.commons.ClientInterface
import cupcakes.br.cbi.models.Client
import cupcakes.br.cbi.utils.Constants
import kotlinx.android.synthetic.main.item_client_content.view.*

/**
 * Created by monitorapc on 16-Oct-17.
 */
class MyClientsAdapter(val clients: ArrayList<Client>) : RecyclerView.Adapter<MyClientsAdapter.ViewHolder>(), INameableAdapter {

    var clients_origin = ArrayList<Client>()

    override fun getCharacterForElement(element: Int): Char {

        var c = clients.get(element).name.get(0)
        if ( c.isDigit() )
            c = '#'

        return c
    }


    override fun onBindViewHolder(holder: MyClientsAdapter.ViewHolder, position: Int) {
        holder?.bind(clients[position], position)
    }

    override fun getItemCount() = clients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_client, parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener{

        //client id and phone are not part of the layout, so we need a var to store this
        var clientID = ""
        var clientPhone = ""

        init {
            itemView.setOnLongClickListener(this)
        }


        override fun onLongClick(p0: View?): Boolean {
            var it = Intent(itemView.context , AddClientActivity::class.java)
            it.putExtra(Constants.EDIT_CLIENT, true)
            it.putExtra(Constants.EDIT_CLIENT_NAME, itemView.textNameClient.text.toString())
            it.putExtra(Constants.EDIT_CLIENT_BIRTHDAY, itemView.textBirthdayClient.text.toString())
            it.putExtra(Constants.EDIT_CLIENT_PHONE, clientPhone)
            it.putExtra(Constants.EDIT_CLIENT_ID, clientID)
            itemView.context.startActivity(it)
            return true
        }

        fun bind(client: Client, pos: Int) = with(itemView){
            textNameClient.text = client.name
            textBirthdayClient.text = client.birthday
            clientPhone = client.phoneNumber
            clientID = client.id.toString()
        }
    }



    fun filter(text: String, cliInterface: ClientInterface){

        if (clients_origin.isEmpty())
            clients_origin.addAll(clients)

        var list_clients = ArrayList<Client>()
        var textSearch = text

        if (text.isEmpty()) {
            list_clients.addAll(clients_origin)
        } else {
            textSearch = text.toLowerCase()
            for (client in clients_origin) {
                if (client.name.toLowerCase().contains(textSearch)) {
                        list_clients.add(client)
                }

            }
        }
        cliInterface.onClientSearch(list_clients)


    }
}