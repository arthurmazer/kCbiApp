package cupcakes.br.cbi.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.ViewGroup
import android.view.View
import android.widget.Adapter
import android.widget.ListView
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.item_client_content.view.*

/**
 * Created by monitorapc on 16-Oct-17.
 */
class MyClientsAdapter(val clients: ArrayList<Client>) : RecyclerView.Adapter<MyClientsAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: MyClientsAdapter.ViewHolder, position: Int) {
        holder?.bind(clients[position])
    }

    override fun getItemCount() = clients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_client, parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(client: Client) = with(itemView){
            textNameClient.text = client.name
            textBirthdayClient.text = client.birthday.toString()
        }
    }
}