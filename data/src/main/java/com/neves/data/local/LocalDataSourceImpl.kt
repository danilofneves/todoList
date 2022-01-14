package com.neves.data.local

import com.neves.data.local.database.TaskDAO
import com.neves.data.mapper.toTask
import com.neves.domain.Either
import com.neves.data.model.TaskRoom
import com.neves.domain.exception.TaskException
import com.neves.domain.model.Task
import java.util.*
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(private val dao: TaskDAO) : LocalDataSource {

    override suspend fun fetch(): Either<List<Task>, Exception>  {
        return try{
            val result = dao.getTaskList()
            Either.Success(result.map {
                it.toTask()
            })
        }
        catch (e:Exception){
            Either.Failure(TaskException.GeneralListException)
        }
    }

    override suspend fun fetchWithDate(from: Date, to:Date): Either<List<Task>, Exception>  {
        return try{
            val result = dao.getTaskWithDateList(from, to)
            Either.Success(result.map {
                it.toTask()
            })
        }
        catch (e:Exception){
            Either.Failure(TaskException.GeneralListException)
        }
    }

    override suspend fun save(task: TaskRoom): Either<Unit, Exception> {
        return try{
            dao.insert(task)
            Either.Success(Unit)
        }
        catch (e:Exception){
            Either.Failure(TaskException.GeneralInsertException)
        }
    }

    override suspend fun update(taskRoom: TaskRoom): Either<Unit, Exception> {
        return try{
            dao.update(taskRoom)
            Either.Success(Unit)
        }
        catch (e:Exception){
            Either.Failure(TaskException.GeneralInsertException)
        }
    }
}
