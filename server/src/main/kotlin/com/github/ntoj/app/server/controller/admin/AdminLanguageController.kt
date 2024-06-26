package com.github.ntoj.app.server.controller.admin

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.annotation.SaCheckRole
import cn.dev33.satoken.annotation.SaMode
import com.github.ntoj.app.server.ext.success
import com.github.ntoj.app.server.model.L
import com.github.ntoj.app.server.model.entities.Language
import com.github.ntoj.app.server.service.LanguageService
import com.github.ntoj.app.shared.model.R
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/language")
@SaCheckLogin
class AdminLanguageController(
    private val languageService: LanguageService,
) {
    @GetMapping
    fun index(
        @RequestParam(required = false, defaultValue = "1") current: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
    ): ResponseEntity<R<L<LanguageDto>>> {
        val list = languageService.get(page = current, pageSize = pageSize)
        val count = languageService.count()
        return R.success(
            200,
            "获取成功",
            L(
                total = count,
                page = current,
                list = list.map { LanguageDto.from(it) },
            ),
        )
    }

    @GetMapping("{id}")
    fun get(
        @PathVariable id: Long,
    ): ResponseEntity<R<LanguageDto>> {
        val language = languageService.get(id)
        return R.success(
            200,
            "获取成功",
            LanguageDto.from(language),
        )
    }

    @PostMapping
    @SaCheckRole(value = ["SUPER_ADMIN"], mode = SaMode.OR)
    fun create(
        @RequestBody @Valid
        languageRequest: LanguageRequest,
    ): ResponseEntity<R<LanguageDto>> {
        return R.success(
            200,
            "创建成功",
            LanguageDto.from(
                languageService.new(
                    Language(
                        languageName = languageRequest.languageName,
                        compileCommand = languageRequest.compileCommand,
                        executeCommand = languageRequest.executeCommand,
                        enabled = languageRequest.enabled,
                        sourceFilename = languageRequest.sourceFilename,
                        targetFilename = languageRequest.targetFilename,
                    ),
                ),
            ),
        )
    }

    @PatchMapping("{id}")
    @SaCheckRole(value = ["SUPER_ADMIN"], mode = SaMode.OR)
    fun update(
        @RequestBody @Valid
        languageRequest: LanguageRequest,
        @PathVariable id: Long,
    ): ResponseEntity<R<LanguageDto>> {
        val language = languageService.get(id)
        if (language.languageName != languageRequest.languageName) {
            language.languageName = languageRequest.languageName
        }
        if (language.compileCommand != languageRequest.compileCommand) {
            language.compileCommand = languageRequest.compileCommand
        }
        if (language.executeCommand != languageRequest.executeCommand) {
            language.executeCommand = languageRequest.executeCommand
        }
        if (language.enabled != languageRequest.enabled) {
            language.enabled = languageRequest.enabled
        }
        if (language.memoryLimitRate != languageRequest.memoryLimitRate) {
            language.memoryLimitRate = languageRequest.memoryLimitRate
        }
        if (language.timeLimitRate != languageRequest.timeLimitRate) {
            language.timeLimitRate = languageRequest.timeLimitRate
        }
        if (language.sourceFilename != languageRequest.sourceFilename) {
            language.sourceFilename = languageRequest.sourceFilename
        }
        if (language.targetFilename != languageRequest.targetFilename) {
            language.targetFilename = languageRequest.targetFilename
        }
        return R.success(
            200,
            "修改成功",
            LanguageDto.from(languageService.update(language)),
        )
    }

    @DeleteMapping("/{id}")
    @SaCheckRole(value = ["SUPER_ADMIN"], mode = SaMode.OR)
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<R<Unit>> {
        languageService.delete(id)
        return R.success(200, "删除成功")
    }
}

data class LanguageDto(
    var languageName: String,
    var compileCommand: String?,
    var executeCommand: String?,
    var enabled: Boolean,
    var id: Long,
    var memoryLimitRate: Int?,
    var timeLimitRate: Int?,
    var sourceFilename: String?,
    var targetFilename: String?,
) {
    companion object {
        fun from(language: Language): LanguageDto {
            return LanguageDto(
                languageName = language.languageName,
                compileCommand = language.compileCommand,
                executeCommand = language.executeCommand,
                enabled = language.enabled,
                id = language.languageId!!,
                memoryLimitRate = language.memoryLimitRate,
                timeLimitRate = language.timeLimitRate,
                sourceFilename = language.sourceFilename,
                targetFilename = language.targetFilename,
            )
        }
    }
}

data class LanguageRequest(
    var languageName: String,
    var compileCommand: String?,
    var executeCommand: String?,
    var enabled: Boolean,
    var memoryLimitRate: Int?,
    var timeLimitRate: Int?,
    var sourceFilename: String,
    var targetFilename: String,
)
