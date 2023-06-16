package be.zvz.clova

import be.zvz.clova.tts.ClovaTTS
import java.io.ByteArrayOutputStream
import java.net.URL
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClovaTest {
    @Test fun testClovaTranslateAutoToJapanese() {
        val classUnderTest = Clova()
        Clova.Translation.values().forEach {
            val translate = classUnderTest.translation.getValue(it).translate(
                language = LanguageSetting(Language.AUTO, Language.JAPANESE),
                text = "수고하셨습니다.\n번역기를 사용한 문장인데 어색하지는 않나요?",
                agreeToUsingTextData = false,
                enableDictionary = true
            )
            println(translate)
            assertNull(translate.errorCode)
            assertNull(translate.errorMessage)
        }
    }

    @Test fun testClovaTranslateKoreanToEnglish() {
        val classUnderTest = Clova()
        Clova.Translation.values().forEach {
            val translate = classUnderTest.translation.getValue(it).translate(
                language = LanguageSetting(Language.KOREAN, Language.ENGLISH),
                text = "수고하셨습니다.\n번역기를 사용한 문장인데 어색하지는 않나요?",
                agreeToUsingTextData = false,
                enableDictionary = true
            )
            println(translate)
            assertNull(translate.errorCode)
            assertNull(translate.errorMessage)
        }
    }

    @Test fun testClovaTranslateJapaneseToKorean() {
        val classUnderTest = Clova()
        Clova.Translation.values().forEach {
            val translate = classUnderTest.translation.getValue(it).translate(
                language = LanguageSetting(Language.JAPANESE, Language.KOREAN),
                text = "ラインID交換する？",
                agreeToUsingTextData = false,
                enableDictionary = true
            )
            println(translate)
            assertNull(translate.errorCode)
            assertNull(translate.errorMessage)
        }
    }

    @Test fun testClovaTranslateJapaneseToEnglish() {
        val classUnderTest = Clova()
        Clova.Translation.values().forEach {
            val translate = classUnderTest.translation.getValue(it).translate(
                language = LanguageSetting(Language.JAPANESE, Language.ENGLISH),
                text = "ラインID交換する？",
                agreeToUsingTextData = false,
                enableDictionary = true
            )
            println(translate)
            assertNull(translate.errorCode)
            assertNull(translate.errorMessage)
        }
    }

    @Test fun testClovaTTSKoreanFemale() {
        val classUnderTest = Clova()
        val speechArr = classUnderTest.tts.getValue(Clova.TTS.CLOVA).doTextToSpeech(
            text = "다람쥐 헌 쳇바퀴 위에 올라가, 3000% 팝필터팝팝",
            speaker = ClovaTTS.SPEAKERS.getValue(Language.KOREAN).female.first(),
            speed = Speed.NORMAL
        )
        println(speechArr.size)
        assertTrue(speechArr.isNotEmpty())
    }

    @Test fun testClovaTTSKoreanMale() {
        val classUnderTest = Clova()
        val speechArr = classUnderTest.tts.getValue(Clova.TTS.CLOVA).doTextToSpeech(
            text = "다람쥐 헌 쳇바퀴 위에 올라가, 3000% 팝필터팝팝",
            speaker = ClovaTTS.SPEAKERS.getValue(Language.KOREAN).male.first(),
            speed = Speed.NORMAL
        )
        println(speechArr.size)
        assertTrue(speechArr.isNotEmpty())
    }

    @Test fun testClovaTTSJapaneseMale() {
        val classUnderTest = Clova()
        val speechArr = classUnderTest.tts.getValue(Clova.TTS.CLOVA).doTextToSpeech(
            text = "お疲れ様でした！",
            speaker = ClovaTTS.SPEAKERS.getValue(Language.JAPANESE).male.first(),
            speed = Speed.NORMAL
        )
        println(speechArr.size)
        assertTrue(speechArr.isNotEmpty())
    }

    @Test fun testClovaTTSSimplifiedChineseFemale() {
        val classUnderTest = Clova()
        val speechArr = classUnderTest.tts.getValue(Clova.TTS.CLOVA).doTextToSpeech(
            text = "吃饭了吗？",
            speaker = ClovaTTS.SPEAKERS.getValue(Language.SIMPLIFIED_CHINESE).male.first(),
            speed = Speed.NORMAL
        )
        println(speechArr.size)
        assertTrue(speechArr.isNotEmpty())
    }

    @Test fun testClovaOCR() {
        fun getImageBytes(imageUrl: String): ByteArray {
            val url = URL(imageUrl)
            val output = ByteArrayOutputStream()
            url.openStream().use { stream ->
                val buffer = ByteArray(4096)
                while (true) {
                    val bytesRead = stream.read(buffer)
                    if (bytesRead < 0) {
                        break
                    }
                    output.write(buffer, 0, bytesRead)
                }
            }
            return output.toByteArray()
        }

        val classUnderTest = Clova()
        val ocr = classUnderTest.ocr.getValue(Clova.OCR.PAPAGO).doOCR(
            language = LanguageSetting(Language.AUTO, Language.KOREAN),
            image = getImageBytes("https://upload.wikimedia.org/wikipedia/commons/a/a6/Japanese_Residence_Card_Sample.jpg")
        )
        println(ocr)
        assertNull(ocr.errorCode)
        assertNull(ocr.errorMessage)
    }
}
