package me.injin.util

const val REQUIRED_NAME = "성함은 필수 입니다."
const val VALUE_IS_EMPTY = "넘어온 값이 비어있습니다."

//아래 enum 클래스 와 함수는 테스트용 이므로, 혹시 사용할 경우 package 별 분리를 권장합니다.
enum class REGEX_TYPE(val pattern: String, val desc: String) {
    ONLY_KOREAN_SENTENCES("^[가-힣|/s]+$", "한글문장만 허용"),
    ONLY_KOREAN_SENTENCES_WITH_SPACES("^[가-힣\\s]*$", "한글문장+공백 허용")
}
fun throwBusinessException(msg: String?): Nothing {
    throw IllegalArgumentException(msg?: "")
}

//trim 후 null 또는 비어있을경우 BusinessException
fun String.TrimAndNullOrEmptyIsThrowException(msg: String?) {
    if(this.trim().isNullOrEmpty()) throwBusinessException("${msg?: VALUE_IS_EMPTY}")
}

/**
 * pattern 과 input value 의 매칭여부를 확인합니다.
 * @return Boolean
 */
infix fun String.regularExpressionCheck(pattern: String) = Regex(pattern).matches(this)

/**
 * 정규식을 검사한뒤, target 을 replaceText 치환 합니다.
 * 정규식 조건에 포함되지 않으면 trim() 만 수행합니다.
 * @return :replace 된 String 객체 또는 trim 함수 수행.
 * @throws :trim() 조건후 null or empty 일 경우 throwBusinessException
 */
fun String.ReplaceTargetStringOrTrim(regexType: REGEX_TYPE, target: String, replaceText: String): String {
    this.TrimAndNullOrEmptyIsThrowException(REQUIRED_NAME)
    return if(this regularExpressionCheck regexType.pattern) this.replace(target, replaceText)
    else this.trim()
}
