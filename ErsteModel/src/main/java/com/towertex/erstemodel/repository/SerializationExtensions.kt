package com.towertex.erstemodel.repository

import kotlinx.serialization.json.Json

internal inline fun <reified T> List<T>.toJson(): String = Json.encodeToString(this)