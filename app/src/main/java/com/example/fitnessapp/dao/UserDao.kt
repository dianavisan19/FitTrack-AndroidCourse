package com.example.fitnessapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnessapp.dao.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Query("""
        SELECT CASE 
            WHEN weight > 0 AND height > 0 AND activityLevel != '' AND age > 0 THEN 1 
            ELSE 0 
        END 
        FROM users 
        WHERE id = :userId
    """)
    suspend fun isProfileComplete(userId: Long): Boolean
}
