package cupcakes.br.cbi.managers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import cupcakes.br.cbi.models.Client
import cupcakes.br.cbi.utils.Constants
import org.jetbrains.anko.db.*
import java.sql.SQLException

/**
 * Created by monitorapc on 16-Oct-17.
 */
class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, Constants.DB_NAME) {

    companion object {
        private var instance: MySqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MySqlHelper {
            if (instance == null) {
                instance = MySqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Constants.DB_TABLE_CLIENTS,true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "name" to TEXT,
                "birthday" to TEXT,
                "phone_number" to TEXT,
                "facebook_url" to TEXT,
                "location" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insertClient(name: String, birthday: String, phoneNumber: String, facebookUrl: String,location: String){
        this.use {
            try{
                insert(Constants.DB_TABLE_CLIENTS,
                        "name" to name,
                        "birthday" to birthday,
                        "phone_number" to phoneNumber,
                        "facebook_url" to facebookUrl,
                        "location" to location)
            }catch (exception: SQLException){
                error("Error with database connection, please contact Tuco ;)")
            }

        }
    }

    fun selectAllClients(): ArrayList<Client> {
        var result: List<Client> = ArrayList<Client>()
        val clients = this.use {
            try{
                result = select(Constants.DB_TABLE_CLIENTS)
                        .orderBy("name", SqlOrderDirection.ASC)
                        .exec { parseList(classParser<Client>()) }  
            }catch(exception: SQLException){
                error("Error with database connection, please contact Tuco ;)")
            }

        }
        return result as ArrayList<Client>
    }

}

// Access property for Context
val Context.database: MySqlHelper
    get() = MySqlHelper.getInstance(applicationContext)