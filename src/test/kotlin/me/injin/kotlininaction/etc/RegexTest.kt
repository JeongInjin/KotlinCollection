package me.injin.kotlininaction.etc

import me.injin.util.REGEX_TYPE.ONLY_KOREAN_SENTENCES
import me.injin.util.REGEX_TYPE.ONLY_KOREAN_SENTENCES_WITH_SPACES
import me.injin.util.ReplaceTargetStringOrTrim
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private const val GAP = " "
class RegexTest {

    val testName0 = "정인진"
    val testName1 = " 정 인 진 "
    val testName2 = " 정인 진 "
    val testName3 = " 정 인진 "
    val testName4 = "정 인 진 "
    val testName5 = " jeong in jin "
    val testName6 = " jeong in jin"
    val testName7 = "jeong in jin "

    @Test
    @DisplayName("한글만 존재하는 정규식을 테스트 합니다.")
    fun RegularExpressionCheckTest1() {
        //given//when
        val matches0 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("ㄱㄱㄱ")
        val matches1 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("ㄱ나니?")
        val matches2 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("고ㅎ장")
        val matches3 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("고흐ㅎ")
        val matches4 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("고흐ㅎ ")
        val matches5 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(" ㅁ고흐")
        val matches6 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("1정인진")
        val matches7 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("정인진2")
        val matches8 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("정1인진")
        val matches9 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("궭뷁솷")

        val oldMatches1 = Regex(ONLY_KOREAN_SENTENCES.pattern).matches(testName0) //기존 정규식.
        val oldMatches2 = Regex(ONLY_KOREAN_SENTENCES.pattern).matches(testName1) //기존 정규식. 공백 존재하면 실패
        val oldMatches3 = Regex(ONLY_KOREAN_SENTENCES.pattern).matches(testName2) //기존 정규식. 공백 존재하면 실패

        //then
        assertThat(matches0).isFalse
        assertThat(matches1).isFalse
        assertThat(matches2).isFalse
        assertThat(matches3).isFalse
        assertThat(matches4).isFalse
        assertThat(matches5).isFalse
        assertThat(matches6).isFalse
        assertThat(matches7).isFalse
        assertThat(matches8).isFalse
        assertThat(matches9).isTrue

        assertThat(oldMatches1).isTrue
        assertThat(oldMatches2).isFalse
        assertThat(oldMatches3).isFalse
    }

    @Test
    @DisplayName("한글만 존재하는 정규식을 테스트 합니다.")
    fun RegularExpressionCheckTest2() {
        //given//when
        val matches0 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches("정인진")
        val matches1 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName1)
        val matches2 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName2)
        val matches3 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName3)
        val matches4 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName4)
        val matches5 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName5)
        val matches6 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName6)
        val matches7 = Regex(ONLY_KOREAN_SENTENCES_WITH_SPACES.pattern).matches(testName7)

        //then
        assertThat(matches0).isTrue
        assertThat(matches1).isTrue
        assertThat(matches2).isTrue
        assertThat(matches3).isTrue
        assertThat(matches4).isTrue
        assertThat(matches5).isFalse
        assertThat(matches6).isFalse
        assertThat(matches7).isFalse
    }

    @Test
    @DisplayName("한글만 존재하는 정규식 테스트가 통과하면 치환 합니다.")
    fun `Extension Function Test`() {
        //given//when
        val test1 = "정인진".ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val test2 = " 정인진".ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val test3 = "정인진 ".ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val test4 = "정 인 진 ".ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")

        val resultName0 = testName0.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName1 = testName1.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName2 = testName2.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName3 = testName3.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName4 = testName4.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")

        val resultName5 = testName5.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName6 = testName6.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")
        val resultName7 = testName7.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "")

        val resultName8 = testName2.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "-")
        val resultName9 = testName3.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "-")
        val resultName10 = testName4.ReplaceTargetStringOrTrim(ONLY_KOREAN_SENTENCES_WITH_SPACES, GAP, "-")
        //then
        assertThat(test1).isEqualTo("정인진")
        assertThat(test2).isEqualTo("정인진")
        assertThat(test3).isEqualTo("정인진")
        assertThat(test4).isEqualTo("정인진")


        assertThat(resultName0).isEqualTo("정인진")
        assertThat(resultName1).isEqualTo("정인진")
        assertThat(resultName2).isEqualTo("정인진")
        assertThat(resultName3).isEqualTo("정인진")
        assertThat(resultName4).isEqualTo("정인진")

        assertThat(resultName5).isEqualTo("jeong in jin")
        assertThat(resultName6).isEqualTo("jeong in jin")
        assertThat(resultName7).isEqualTo("jeong in jin")

        assertThat(resultName8).isEqualTo("-정인-진-")
        assertThat(resultName9).isEqualTo("-정-인진-")
        assertThat(resultName10).isEqualTo("정-인-진-")
    }


    @Test
    fun test() {
        if (" ".isNullOrEmpty()) {
            println("비어 있음")
        } else {
            println("값이 있음")
        }
    }

}