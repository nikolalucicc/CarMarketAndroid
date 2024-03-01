package com.carmarket.model.mapper

import com.carmarket.model.dto.ImageDTO
import com.carmarket.model.responseBody.ImageResponseBody

class ImageResponseBodyToImageDTOMapper : Mapper<ImageResponseBody, ImageDTO> {
    override fun mapTo(responseBody: ImageResponseBody): ImageDTO =
        ImageDTO(
            id = responseBody.id,
            url = responseBody.url
        )
}