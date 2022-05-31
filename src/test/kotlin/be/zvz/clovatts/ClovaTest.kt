package be.zvz.clovatts

import kotlin.test.Test
import kotlin.test.assertTrue

class ClovaTest {
    @Test fun testClova() {
        val classUnderTest = Clova()
        val speechArr = classUnderTest.getSpeech(
            text = "다람쥐 헌 쳇바퀴 위에 올라가, 3000% 팝필터팝팝",
            speaker = Constants.SPEAKERS.getValue(Language.KOREAN).female.first(),
            speed = Speed.NORMAL
        )
        println(speechArr.size)
        assertTrue(speechArr.isNotEmpty())
    }
}
