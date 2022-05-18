package com.example.mvvmg.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mvvmg.database.entity.UserEntity

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("select * from userentity")
    fun getUsers(): List<UserEntity>

}