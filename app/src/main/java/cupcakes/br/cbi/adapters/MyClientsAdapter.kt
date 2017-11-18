package cupcakes.br.cbi.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.turingtechnologies.materialscrollbar.INameableAdapter
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Client
import kotlinx.android.synthetic.main.item_client_content.view.*

/**
 * Created by monitorapc on 16-Oct-17.
 */
class MyClientsAdapter(val clients: ArrayList<Client>) : RecyclerView.Adapter<MyClientsAdapter.ViewHolder>(), INameableAdapter {

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            Log.d("lavai", "uai")
            Log.d("lavai3", "asd " + adapterPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            Log.d("lavai", "asd " + adapterPosition)
            return false
        }

        fun bind(client: Client, pos: Int) = with(itemView){
            textNameClient.text = client.name
            textBirthdayClient.text = client.birthday.toString()
        }
    }
}