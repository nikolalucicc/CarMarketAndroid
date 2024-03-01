package com.carmarket.model.mapper

import com.carmarket.model.dto.UserDTO
import com.carmarket.model.responseBody.UserResponseBody

class UserResponseBodyToUserDTOMapper : Mapper<UserResponseBody, UserDTO> {

    override fun mapTo(responseBody: UserResponseBody): UserDTO =
        UserDTO(
            id = responseBody.id,
            email = responseBody.email,
            password = responseBody.password,
            username = responseBody.username,
            firstName = responseBody.firstName,
            lastName = responseBody.lastName,
            registrationDate = responseBody.registrationDate
        )

}