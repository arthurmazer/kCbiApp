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

        db.createTable(Constants.DB_TABLE_CLIENTS,false,
                "_id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
                Constants.DB_CLIENTS_COLUMN_NAME to TEXT,
                Constants.DB_CLIENTS_COLUMN_BIRTHDAY to TEXT,
                Constants.DB_CLIENTS_COLUMN_PHONE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun insertClient(cli: Client){
        this.use {
            try{
                insert(Constants.DB_TABLE_CLIENTS,
                        Constants.DB_CLIENTS_COLUMN_NAME to cli.name,
                        Constants.DB_CLIENTS_COLUMN_BIRTHDAY to cli.birthday,
                        Constants.DB_CLIENTS_COLUMN_PHONE to cli.phoneNumber)
            }catch (exception: SQLException){
                error("Error with database connection, please contact Tuco ;)")
            }

        }
    }

    fun updateClient(id: Int, newCli: Client){
        this.use {
            try{
                update(Constants.DB_TABLE_CLIENTS,
                        Constants.DB_CLIENTS_COLUMN_NAME to newCli.name,
                        Constants.DB_CLIENTS_COLUMN_BIRTHDAY to newCli.birthday,
                        Constants.DB_CLIENTS_COLUMN_PHONE to newCli.phoneNumber)
                        .whereArgs(Constants.DB_CLIENTS_COLUMN_ID + " = {userId}", "userId" to id)
                        .exec()
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