package com.carmarket.model.mapper

import com.carmarket.model.dto.AdDTO
import com.carmarket.model.responseBody.AdResponseBody

class AdResponseBodyToAdDTOMapper(
    private var imageResponseBodyToImageDTOMapper: ImageResponseBodyToImageDTOMapper
) : Mapper<AdResponseBody, AdDTO> {

    override fun mapTo(responseBody: AdResponseBody): AdDTO =
        AdDTO(
            id = responseBody.id,
            description = responseBody.description,
            brand = responseBody.brand,
            model = responseBody.model,
            year = responseBody.year,
            price = responseBody.price,
            condition = responseBody.condition,
            mileage = responseBody.mileage,
            engineType = responseBody.engineType,
            engineHp = responseBody.engineHp,
            engineKw = responseBody.engineKw,
            engineCubic = responseBody.engineCubic,
            transmission = responseBody.transmission,
            city = responseBody.city,
            country = responseBody.country,
            postedDate = responseBody.postedDate,
            images = responseBody.images.map { imageResponseBodyToImageDTOMapper.mapTo(it) }
        )

}