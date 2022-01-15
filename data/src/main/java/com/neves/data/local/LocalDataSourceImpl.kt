package com.neves.data.local

import com.neves.data.local.database.TaskDAO
import com.neves.data.local.exception.SaveLocalException
import com.neves.data.mapper.toTask
import com.neves.data.model.TaskRoom
import com.neves.domain.model.Task
import java.lang.Exception
import java.util.*
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(private val dao: TaskDAO) : LocalDataSource {

    override suspend fun fetch(): List<Task>  {
        return runInRoom {
            dao.getTaskList().map {
                it.toTask()
            }
        }
    }

    override suspend fun fetchWithDate(from: Date, to:Date): List<Task>  {
        return runInRoom {
            dao.getTaskWithDateList(from, to).map {
                it.toTask()
            }
        }
    }

    override suspend fun save(taskRoom: TaskRoom) {
        return runInRoom {
            dao.insert(taskRoom)
        }
    }

    override suspend fun update(taskRoom: TaskRoom) {
        return runInRoom {
            dao.update(taskRoom)
        }
    }

    private suspend fun <T> runInRoom(call: suspend () -> T): T {
        //CASO FOSSE UM DATASOURCE DE CONEXÃO COM A INTERNET, ESSE MÉTODO, OU ATÉ UMA CLASSE INJETADA,
        //PODERIA VERIFICAR SE TEM CONEXÃO COM A INTERNET E LANÇAR UMA EXCEÇÃO DO TIPO NOCONECTIONEXCEPTION
        try {
            return call.invoke()
        } catch (e: Exception) {
            throw SaveLocalException()
        }
    }
}
