package com.github.ntoj.app.server.service

import com.github.ntoj.app.server.model.entities.JudgerSystemStatus
import com.github.ntoj.app.server.repository.JudgerSystemStatusRepository
import org.springframework.stereotype.Service

interface JudgerSystemStatusService {
    fun findByJudgerId(id: String): JudgerSystemStatus?

    fun new(judgerSystemStatus: JudgerSystemStatus)

    fun update(judgerSystemStatus: JudgerSystemStatus)
}

@Service
class JudgerSystemStatusServiceImpl(
    private val judgerSystemStatusRepository: JudgerSystemStatusRepository,
) : JudgerSystemStatusService {
    override fun findByJudgerId(id: String): JudgerSystemStatus? {
        return judgerSystemStatusRepository.findByJudgerId(id).orElse(null)
    }

    override fun new(judgerSystemStatus: JudgerSystemStatus) {
        judgerSystemStatusRepository.save(judgerSystemStatus)
    }

    override fun update(judgerSystemStatus: JudgerSystemStatus) {
        judgerSystemStatusRepository.save(judgerSystemStatus)
    }
}
