package cupcakes.br.cbi.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cupcakes.br.cbi.R
import cupcakes.br.cbi.models.Menu
import kotlinx.android.synthetic.main.content_menu_food.view.*

/**
 * Created by arthurmazer on 21-Nov-17.
 */
class MyMenuAdapter(val foods: ArrayList<Menu>)  : RecyclerView.Adapter<MyMenuAdapter.ViewHolder>() {

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(foods[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_menu_food, parent,false)
        return MyMenuAdapter.ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener{
        override fun onLongClick(p0: View?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun bind(food: Menu, pos: Int) = with(itemView){
            tvTitleMenu.text = food.nameFood
            tvPriceMenu.text = food.price.toString()
        }

    }
}