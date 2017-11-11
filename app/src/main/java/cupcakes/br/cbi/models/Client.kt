package cupcakes.br.cbi.models

import android.content.Context
import cupcakes.br.cbi.managers.MySqlHelper
import java.util.*

/**
 * Created by monitorapc on 16-Oct-17.
 */
class Client(val id: Int = 1,val name: String, val birthday: String, val phoneNumber: String){

    fun saveThisClient(ctx: Context): Boolean{
        val db = MySqlHelper(ctx)

        db.insertClient(this)
        return true
    }
}




//wtf it's so easy