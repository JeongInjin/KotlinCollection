package me.injin.util

const val REQUIRED_NAME = "성함은 필수 입니다."

//아래 enum 클래스 와 함수는 테스트용 이므로, 혹시 사용할 경우 package 별 분리를 권장합니다.
enum class REGEX_TYPE(val pattern: String) {
    KOREAN_WITH_SPACES("^[가-힣\\s]*$")
}
fun throwBusinessException(msg: String?): Nothing {
    throw IllegalArgumentException(msg?: "")
}

/**
 * 해당 String 객체를 받아 진행합니다.
 * @param regularExpressionTargetType: 어떠한 정규식을 테스트할것인지를 나타냅니다.
 * @return Boolean
 * @see (필수): 본 함수는 [읿반 유저] 대상으로 진행됩니다.
 * @see (ko): [가-힣 까지의 한글 + 공뱍 허용] 조건을 검색합니다.
 */
infix fun String.RegularExpressionCheck(regularExpressionTargetType: REGEX_TYPE): Boolean {
    return when (regularExpressionTargetType) {
        REGEX_TYPE.KOREAN_WITH_SPACES -> Regex(REGEX_TYPE.KOREAN_WITH_SPACES.pattern).matches(this)
    }
}

/**
 * 정규식을 검사한뒤, target 을 replaceText 치환 합니다.
 * 정규식 조건에 포함되지 않으면 trim() 만 수행합니다.
 * @return :replace 된 String 객체.
 * @throws :trim() 조건후 null or empty 일 경우 throwBusinessException
 */
fun String.ReplaceTargetStringOrTrim(regexType: REGEX_TYPE, target: String, replaceText: String): String {
    if(this.trim().isNullOrEmpty()) throwBusinessException(REQUIRED_NAME)
    return if(this RegularExpressionCheck regexType) this.replace(target, replaceText)
    else this.trim()
}

class StringUtils {
}