package com.app.androidjetpack.data.source.local.room

import androidx.lifecycle.LiveData
import com.app.androidjetpack.data.source.local.ItemEntity

class LocalDataSource private constructor(private val mAcademyDao: AcademyDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(academyDao: AcademyDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(academyDao)
    }

    fun getAllCourses(): LiveData<List<ItemEntity>> = mAcademyDao.getCourses()

//    fun getBookmarkedCourses(): LiveData<List<ItemEntity>> = mAcademyDao.getBookmarkedCourse()

//    fun getCourseWithModules(courseId: String): LiveData<CourseWithModule> =
//        mAcademyDao.getCourseWithModuleById(courseId)

//    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> =
//        mAcademyDao.getModulesByCourseId(courseId)

//    fun insertCourses(courses: List<ItemEntity>) = mAcademyDao.insertCourses(courses)

//    fun insertModules(modules: List<ModuleEntity>) = mAcademyDao.insertModules(modules)

//    fun setCourseBookmark(course: ItemEntity, newState: Boolean) {
//        course.bookmarked = newState
//        mAcademyDao.updateCourse(course)
//    }

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
