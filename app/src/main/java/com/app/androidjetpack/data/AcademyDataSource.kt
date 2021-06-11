package com.app.androidjetpack.data

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.source.local.entity.ItemEntity

interface AcademyDataSource {

    fun getAllMovies(): List<ItemEntity>

//    fun getBookmarkedCourses(): List<ItemEntity>
//
//    fun getCourseWithModules(courseId: String): ItemEntity
//
//    fun getAllModulesByCourse(courseId: String): List<ItemEntity>
//
//    fun getContent(courseId: String, moduleId: String): ItemEntity
}