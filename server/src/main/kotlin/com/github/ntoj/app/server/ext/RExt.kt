package com.github.ntoj.app.server.ext

import com.github.ntoj.app.shared.model.R
import org.springframework.http.ResponseEntity

fun <T> R.Companion.success(
    code: Int,
    message: String,
    data: T? = null,
): ResponseEntity<R<T>> {
    return ResponseEntity.status(code).body(R(code, message, data))
}

fun R.Companion.fail(
    code: Int,
    message: String,
    requestId: String? = null,
): ResponseEntity<R<Void>> {
    return ResponseEntity.status(code).body(R(code, message, null, requestId))
}
