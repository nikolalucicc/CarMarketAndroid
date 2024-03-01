package com.carmarket.model.mapper

import com.carmarket.model.dto.AdDTO
import com.carmarket.model.dto.FavoriteDTO
import com.carmarket.model.dto.UserDTO
import com.carmarket.model.responseBody.AdResponseBody
import com.carmarket.model.responseBody.FavoriteResponseBody
import com.carmarket.model.responseBody.UserResponseBody

class FavoriteResponseBodyToFavoriteDTOMapper(
    private val adResponseBodyToAdDTOMapper: AdResponseBodyToAdDTOMapper,
    private val userResponseBodyToUserDTOMapper: UserResponseBodyToUserDTOMapper
) : Mapper<FavoriteResponseBody, FavoriteDTO> {

    override fun mapTo(responseBody: FavoriteResponseBody): FavoriteDTO =
        FavoriteDTO(
            id = responseBody.id,
            user = responseBody.user.toDTO(),
            ad = responseBody.ad.toDTO()
        )

    private fun AdResponseBody.toDTO(): AdDTO =
        adResponseBodyToAdDTOMapper.mapTo(this)

    private fun UserResponseBody.toDTO(): UserDTO =
        userResponseBodyToUserDTOMapper.mapTo(this)
}