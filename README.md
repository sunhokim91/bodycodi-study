# java-stream
레드블루 사내 스터디자료

## 시나리오
프록시 패턴을 사용하여 성능측정해보기

## 메모
자바 7에서는 더 쉽게 병렬화를 수행하면서 에러를 최소화 할 수 있도록 포크/조인 프레임워크를 제공

병렬스트림은 내부적으로 ForkJoinPool을 사용한다. -> 기본적으로 ForkJoinPool은 Runtime.getRuntime().availableProcessor()에 반환되는 값에 상응하는 스레를 갖는다.

성능을 최적화할 때 세가지 규칙 기억
첫째도 측정, 둘째도 측정, 셋째도 측정

자바 마이크로벤치마크 하니스(Java MicrobenchMark Harness)

stack overflow error -> 발생원인 조사