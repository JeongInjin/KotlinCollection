#4장 - 추상화 설계

---
- item26: 함수 내부의 추상화 레벨을 통일하라.
  - 추상화 레벨
    - 높은 레벨로 갈수록 물리 장치로부터 점점 멀어집니다.
    - 높은 레벨일 수록 프로세서로부터 멀어진다고 표현함
    - 높은 레벨일수룍 단숨함을 얻지만, 제어력을 잃습니다.
      - ex) C 언어는 메모리 관리를 직접 할 수 있지만, 자바는 가비지 컬렉터가 자동으로 메모리를 관리해 줍니다.(따라서 메모리 사용을 최적화하는 것이 힘듭니다.)
    - 코드도 추상화를 계층처럼 만들어서 사용할 수 있습니다. 이를 위한 기본적인 도구가 "함수"이다.
    - 함수도 높은 레벨과 낮은 레벨을 구분해서 사용해야 한다는 원칙이 있다.
      - 이를 추상화 레벨 통일(Single Level of Abstraction(SLA))원칙이라 부른다.
```kotlin
//추상화 레벨이 통일 되지 않은 케이스
class CoffeeMachine {
  
  fun makeCoffee() {
    // 수백 개의 변수를 선언
    // 복잡한 로직을 처리
    //  낮은 수준의 최적화도 여기에서 처리 
  }
  
}
```
```kotlin
//추상화 레벨이 통일된 케이스
class CoffeeMachine {
  
  // 높은 레벨
  fun makeCoffee() {
    boilWater()
    brewCoffee()
    pourCoffee()
    pourMilk()
  }
  
  // 낮은 레벨
  private fun boilWater() { // ... }
  private fun brewCoffee() { // ... }
  private fun pourCoffee() { // ... }
  private fun pourMilk() { // ... }
  
}
```
- 함수는 간단해야 한다.
- 함수는 작아야 하며, 최소한의 책임만을 가져야 한다.
- 어떤 함수가 다른 함수보다 좀 복잡하다면, 일부 부분을 추출해서 추상화 하는 것이 좋다.
---

- item27: 변화로부터 코드를 보호하려면 추상화를 사용하라.
  - 추상화로 실질적인 코드를 숨기면, 사용자가 세부 사항을 알지 못해도 괜찮다는 장점이 있다.
  - 추상화의 몇가지 방법
    - 상수로 추출한다.
    - 동작을 함수로 래핑한다.
    - 함수를 클래스로 래핑한다.
    - 인터페이스 뒤에 클래스를 숨긴다.

- 추상화는 단순하게 중복성을 제거해서 코드를 구성하기 위한 것이 아닙니다.
- 추상화는 코드를 변경해야 할 때 도움이 됩니다.,
- 극단적인 추상화는 좋치않으니, 여러 경험을 통하여 균형을 찾아야 한다.

---




