package com.carmarket.utils

import android.util.Base64
import org.json.JSONException
import org.json.JSONObject

object TokenUtil {
    fun getUserIdFromToken(token: String): Long? {
        val tokenParts = token.split(".")
        if (tokenParts.size != 3) {
            return null
        }
        val body = tokenParts[1]
        val decodedBody = String(Base64.decode(body, Base64.URL_SAFE), Charsets.UTF_8)
        try {
            val jsonBody = JSONObject(decodedBody)
            return jsonBody.optLong("id")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}
