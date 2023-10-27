package com.kick.npl.data.remote.dto

import com.kick.npl.data.remote.dto.Code
import com.kick.npl.data.remote.dto.Region

data class Result(
    val code: Code,
    val name: String,
    val region: Region
)