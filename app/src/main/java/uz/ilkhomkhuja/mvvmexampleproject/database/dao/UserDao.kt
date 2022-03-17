package uz.ilkhomkhuja.mvvmexampleproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.ilkhomkhuja.mvvmexampleproject.database.entites.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    @Query("SELECT * FROM Users")
    suspend fun getAllUsers(): List<User>

}