package cupcakes.br.cbi.database.dao

/**
 * Created by arthu on 10/09/2018.
 */
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import cupcakes.br.cbi.database.dao.entity.Customer

@Dao
abstract class CustomerDao {

    @Query("SELECT * FROM customers")
    abstract fun queryAll(): LiveData<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(customer: Customer)

    @Update
    abstract fun update(customer: Customer)
}
