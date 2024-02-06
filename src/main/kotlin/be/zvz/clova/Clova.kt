package be.zvz.clova

import be.zvz.clova.translation.ocr.PapagoOCR
import be.zvz.clova.translation.text.N2MTTranslate
import be.zvz.clova.translation.text.NSMTTranslate
import be.zvz.clova.tts.ClovaTTS
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

class Clova
    @JvmOverloads
    constructor(
        private val httpClient: HttpClient =
            HttpClient {
                install(ContentNegotiation) {
                    jackson {
                        configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                    }
                }
            },
    ) {
        private val objectMapper =
            jacksonObjectMapper().apply {
                configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            }

        val translation by lazy {
            mapOf(
                Translation.N2MT to N2MTTranslate(httpClient),
                Translation.NSMT to NSMTTranslate(httpClient),
            )
        }

        val tts by lazy {
            mapOf(
                TTS.CLOVA to ClovaTTS(httpClient),
            )
        }

        val ocr by lazy {
            mapOf(
                OCR.PAPAGO to PapagoOCR(httpClient),
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
