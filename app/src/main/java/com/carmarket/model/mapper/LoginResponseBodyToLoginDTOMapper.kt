package com.carmarket.model.mapper

import com.carmarket.model.dto.LoginDTO
import com.carmarket.model.responseBody.LoginResponseBody

class LoginResponseBodyToLoginDTOMapper : Mapper<LoginResponseBody, LoginDTO>{

    override fun mapTo(responseBody: LoginResponseBody): LoginDTO =
        LoginDTO(
            accessToken = responseBody.accessToken,
            expiresIn = responseBody.expiresIn
        )

}