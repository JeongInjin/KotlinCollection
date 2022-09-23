package me.injin.util

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
fun String.RegularExpressionCheck(regularExpressionTargetType: String): Boolean {
    return when (regularExpressionTargetType.toUpperCase()) {
        "KO" -> Regex("^[가-힣\\s]*$").matches(this)
        else -> {false}
    }
}

/**
 * 정규식을 검사한뒤, target 을 replaceText 치환 합니다.
 * 정규식 조건에 포함되지 않으면 trim() 만 수행합니다.
 * @return replace 된 String 객체.
 * @throws trim() 조건후 null or empty 일 경우 IllegalArgumentException
 */
fun String.ReplaceTargetStringOrTrim(regexType: String, target: String, replaceText: String): String {
    if(this.trim().isNullOrEmpty()) throwBusinessException("고객 성함은 필수 입니다.")
    if(!this.RegularExpressionCheck(regexType)) return this.trim()
    return this.replace(target, replaceText)
}

class StringUtils {
}