package com.github.ntoj.app.server.controller

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.stp.StpUtil
import com.github.ntoj.app.server.exception.AppException
import com.github.ntoj.app.server.ext.success
import com.github.ntoj.app.server.model.dtos.GroupDto
import com.github.ntoj.app.server.service.GroupService
import com.github.ntoj.app.server.service.UserService
import com.github.ntoj.app.shared.model.R
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupController(
    val groupService: GroupService,
    val userService: UserService,
) {
    @SaCheckLogin
    @GetMapping("{id}")
    fun get(
        @PathVariable id: Long,
    ): ResponseEntity<R<GroupDto>> {
        val user = userService.getUserById(StpUtil.getLoginIdAsLong())
        val group = groupService.get(id)
        if (!group.users.contains(user)) {
            throw AppException("无权限", 403)
        }
        return R.success(200, "获取成功", GroupDto.from(group))
    }
}
