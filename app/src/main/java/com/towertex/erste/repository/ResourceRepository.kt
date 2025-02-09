package com.towertex.erste.repository

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import androidx.core.content.ContextCompat

// This repository is intended for all resources retrieval if so we can override any resource if needed
// e.g. when the resources are defined and/or selected by server on runtime
// For the demo app this just calls standard Android resources
class ResourceRepository(
    private val appContext: Context
): ResourceRepositoryContract {

    override fun getString(resId: Int) = appContext.getString(resId)

    override fun getString(resId: Int, vararg values: Any) = appContext.getString(resId, *values)

    override fun getDimensionPixelSize(resId: Int) = appContext.resources.getDimensionPixelSize(resId)

    override fun getInteger(resId: Int) = appContext.resources.getInteger(resId)

    override fun getColor(resId: Int) = appContext.getColor(resId)

    override fun getDrawable(resId: Int) = ContextCompat.getDrawable(appContext, resId)

    override fun getPixelSizeFromDimens(dimId: Int) = appContext.resources.getDimension(dimId)

    override fun getFloat(resId: Int): Float = TypedValue().also { appContext.resources.getValue(resId, it, true) }.float

    override fun getColorStateList(resId: Int): ColorStateList = appContext.resources.getColorStateList(resId, null)
}