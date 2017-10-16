package cupcakes.br.cbi.managers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import cupcakes.br.cbi.utils.Constants
import org.jetbrains.anko.db.*

/**
 * Created by monitorapc on 16-Oct-17.
 */
class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

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
            insert(Constants.DB_TABLE_CLIENTS,
                    "name" to name,
                    "birthday" to birthday,
                    "phone_number" to phoneNumber,
                    "facebook_url" to facebookUrl,
                    "location" to location)
        }
    }

    fun selectAllClients(){
        this.use {
            select(Constants.DB_TABLE_CLIENTS)
        }
    }

}

// Access property for Context
val Context.database: MySqlHelper
    get() = MySqlHelper.getInstance(applicationContext)