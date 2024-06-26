package com.github.ntoj.app.server.service

import com.github.ntoj.app.server.model.entities.ContestClarificationResponse
import com.github.ntoj.app.server.repository.ContestClarificationResponseRepository
import org.springframework.stereotype.Service

interface ContestClarificationResponseService {
    fun get(id: Long): ContestClarificationResponse

    fun get(
        onlyVisible: Boolean = false,
        page: Int = 1,
        pageSize: Int = Int.MAX_VALUE,
        desc: Boolean = false,
    ): List<ContestClarificationResponse>

    fun count(onlyVisible: Boolean = false): Long

    fun add(contest: ContestClarificationResponse): ContestClarificationResponse

    fun update(contest: ContestClarificationResponse): ContestClarificationResponse

    fun delete(id: Long)

    fun exists(id: Long): Boolean
}

@Service
class ContestClarificationResponseServiceImpl(
    private val contestClarificationResponseRepository: ContestClarificationResponseRepository,
) : ContestClarificationResponseService {
    override fun get(id: Long): ContestClarificationResponse {
        TODO("Not yet implemented")
    }

    override fun get(
        onlyVisible: Boolean,
        page: Int,
        pageSize: Int,
        desc: Boolean,
    ): List<ContestClarificationResponse> {
        TODO("Not yet implemented")
    }

    override fun count(onlyVisible: Boolean): Long {
        TODO("Not yet implemented")
    }

    override fun add(contest: ContestClarificationResponse): ContestClarificationResponse {
        return contestClarificationResponseRepository.save(contest)
    }

    override fun update(contest: ContestClarificationResponse): ContestClarificationResponse {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun exists(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}
