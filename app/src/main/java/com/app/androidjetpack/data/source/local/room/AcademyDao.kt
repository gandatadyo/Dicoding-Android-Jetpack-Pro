package com.app.androidjetpack.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.app.androidjetpack.data.source.local.entity.ItemEntity

@Dao
interface AcademyDao {

    @Query("SELECT * FROM courseentities")
    fun getCourses(): DataSource.Factory<Int, ItemEntity>

    @Query("SELECT * FROM courseentities where bookmarked = 1")
    fun getBookmarkedCourse(): DataSource.Factory<Int, ItemEntity>

    @Transaction
    @Query("SELECT * FROM courseentities WHERE itemId = :courseId")
    fun getCourseWithModuleById(courseId: String): LiveData<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourses(courses: List<ItemEntity>)

    @Update
    fun updateCourse(course: ItemEntity)

//    @Query("SELECT * FROM moduleentities WHERE courseId = :courseId")
//    fun getModulesByCourseId(courseId: String): LiveData<List<ModuleEntity>>

//    @Query("SELECT * FROM moduleentities WHERE moduleId = :moduleId")
//    fun getModuleById(moduleId: String): LiveData<ModuleEntity>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertModules(module: List<ModuleEntity>)

//    @Update
//    fun updateModule(module: ModuleEntity)

//    @Query("UPDATE moduleentities SET content = :content WHERE moduleId = :moduleId")
//    fun updateModuleByContent(content: String, moduleId: String)
}