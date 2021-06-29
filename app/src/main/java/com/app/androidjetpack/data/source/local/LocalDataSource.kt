package com.app.androidjetpack.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.app.androidjetpack.data.source.local.entity.ItemEntity
import com.app.androidjetpack.data.source.local.room.AcademyDao

class LocalDataSource private constructor(private val mAcademyDao: AcademyDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(academyDao: AcademyDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(academyDao)
    }

    fun getAllCourses(): DataSource.Factory<Int, ItemEntity> = mAcademyDao.getCourses()

    fun getBookmarkedCourses(): DataSource.Factory<Int, ItemEntity> = mAcademyDao.getBookmarkedCourse()


    fun getCourseWithModules(courseId: String): LiveData<ItemEntity> =
        mAcademyDao.getCourseWithModuleById(courseId)

//    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> =
//        mAcademyDao.getModulesByCourseId(courseId)

    fun insertCourses(courses: List<ItemEntity>) = mAcademyDao.insertCourses(courses)

//    fun insertModules(modules: List<ItemEntity>) = mAcademyDao.insertModules(modules)

    fun setCourseBookmark(course: ItemEntity, newState: Boolean) {
        course.bookmarked = newState
        mAcademyDao.updateCourse(course)
    }

//    fun getModuleWithContent(moduleId: String): LiveData<ModuleEntity> =
//        mAcademyDao.getModuleById(moduleId)

//    fun updateContent(content: String, moduleId: String) {
//        mAcademyDao.updateModuleByContent(content, moduleId)
//    }

//    fun setReadModule(module: ModuleEntity) {
//        module.read = true
//        mAcademyDao.updateModule(module)
//    }
}
