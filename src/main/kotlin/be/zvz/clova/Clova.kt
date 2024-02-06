package be.zvz.clova

import be.zvz.clova.translation.ocr.PapagoOCR
import be.zvz.clova.translation.text.N2MTTranslate
import be.zvz.clova.translation.text.NSMTTranslate
import be.zvz.clova.tts.ClovaTTS
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient

class Clova
    @JvmOverloads
    constructor(
        private val okHttpClient: OkHttpClient =
            OkHttpClient
                .Builder()
                .build(),
        private val objectMapper: ObjectMapper =
            jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false),
    ) {
        val translation by lazy {
            mapOf(
                Translation.N2MT to N2MTTranslate(okHttpClient, objectMapper),
                Translation.NSMT to NSMTTranslate(okHttpClient, objectMapper),
            )
        }

        val tts by lazy {
            mapOf(
                TTS.CLOVA to ClovaTTS(okHttpClient),
            )
        }

        val ocr by lazy {
            mapOf(
                OCR.PAPAGO to PapagoOCR(okHttpClient, objectMapper),
            )
        }

        enum class Translation {
            N2MT,
            NSMT,
        }

        enum class TTS {
            CLOVA,
        }

        enum class OCR {
            PAPAGO,
        }
    }
