package me.whiteship.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * > 테스트 코드 바로 생성하는 방법
 *
 * 1. 테스트하고 싶은 코드에 들어가서 클래스이름을 우클릭
 * 2. Go To
 * 3. test
 * 4. 이름을 정하고 테스트 시작
 *
 *
 * > 테스트 케이스 이름을 결정하는 방법
 *
 * @DisplayNameGeneration 어노테이션
 *
 * 테스트 이름을 어떻게 표현할 것인지 결정
 * DisplayNameGenerator.ReplaceUnderscores.class => _를 공백으로 치환해줌
 * */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AssertTests {

    /**
     * @BeforeAll, @AfterAll
     * 테스트 시작 전, 또는 테스트를 완료한 이후 딱 한번만 호출 됨
     * return type이 없어야 하며
     * static 키워드를 가지고 있어야 한다.
     * */
    @BeforeAll
    static void start_once_before_test() {
        System.out.println("start once before test : need static and return void");
    }

    @AfterAll // 모든 테스트 실행 이후 한번만
    static void start_once_after_test() {
        System.out.println("start once after test : need static and return void");
    }

    /**
     * @BeforeEach, @AfterEach
     * 각각의 테스트 이전, 이후에 한번씩 실행
     * 굳이 static일 필요 없음
     * */
    @BeforeEach
    void beforeEach() {
        System.out.println("start before each test");
    }

    @AfterEach
    void afterEach() {
        System.out.println("start after each test");
    }

    @Test
    void test_method() {
        System.out.println("test method 라는 이름으로 실행됩니다.");
    }

    /**
     * @Disabled
     * 메소드를 잠시 테스트하고 싶지 않을 때 사용
     * */
    @Test
    @Disabled
    void test_method_disabled() {
        System.out.println("이 메소드는 실행되지 않습니다.");
    }

    @Test
    @DisplayName("📌 테스트 이름 수정하기")
    void test_name() {
        System.out.println("테스트 이름 수정하기");
    }

    /**
     *
     * */
    @Test
    @DisplayName("에러를 받을 수 있게 해주는 assert 구문")
    void assert_test_error() {
        /**
         * assertThrows 메소드를 이용하면
         * 2 번째 인자로 전달한 람다식을 수행하면서
         * 발생하는 예외상황이 기대값과 같은지 확인합니다.
         * 그리고 같다면, 예외를 반환합니다.
         * 이는 변수로 받을 수 있습니다.
         * */
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        System.out.println(exception.getMessage());
    }

    @Test
    @DisplayName("assert 구문에 대한 기본적인 문법")
    void assert_test() {
        Study study = null;
        // 하나의 테스트가 실패하면 다음 테스트는 실행 되지 않는데, assertAll 로 묶으면 실패했음을 한번에 알 수 있음
        /**
         * 한번에 테스트 하기
         *
         * assertAll의 인자로
         * 어선셜 구문을 람다로 보내면
         * 여러건의 테스트를 한번에 진행할 수 있습니다.
         *
         * 이때, 하나의 어선셜 구문에서 예외가 발생해도
         * 메소드 실행을 멈추지 않고 끝까지 실행합니다.
         * */
        assertAll(
                // 기대 값이 null 인지
                () -> assertNotNull(study),
                // 첫번째 인자가 기대값, 두번째는 테스트하기 원하는 대상, 세번째 인자는 실패시 출력될 값
                // 세 번째 인자를 람다(Supplier 타입)로 넘겨주면, 문자열 연산 등등, 실패하지 않았을 때 실행하지 않기 때문에 성능상의 이점이 있음
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                // 기대값이 true인지 확인합니다.
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 인원은 0명 이상이어야 한다.")
        );
        System.out.println("create");
    }

    /**
     * 시간 안에 실행이 되는지 확인하는 문법
     *
     * Duration.ofMillis()
     * Duration.ofSeconds()
     * Duration.ofMinutes()
     * 위, 각각의 시간을 사용할 수 있다.
     *
     * 아래 메소드의 문제점은
     * 람다로 전달해준 실행 대상(A)이,
     * timeout 되었더라도
     * A 메소드가 끝나기 전까지 기다려야 함.
     *
     * assertTimeoutPreemptively 메소드를 사용하면, 시간을 넘어갔을때, 끝까지 기다리지 않고 바로 종료가 됨
     * 그런데 주의 해야할 점은 ThreadLocal 을 사용하는 코드가 저 블록 안에 있다면, 예상치 못한 결과가 나올수도 있음
     * ThreadLocal 란? 쓰레드는 Thread safe 를 위해서, 각각의 쓰레드만 사용할 수 있는 일종의 지역변수를 지원하는데, 이를 ThreadLocal 라고 함
     * 다른 쓰레드에서는 공유가 안되는 것이 기본이라서, 스프링이 만든 트랙젝션 처리가 제대로 적용 안될 수도 있음
     * 즉, rollback을 했을 때 rollback이 안되고 commit이 되어 버릴 수 있음
     * 구체적으로 트랜젝션을 가진, 쓰레드와 다른 쓰레드를 생성하기 때문이다
     * */
    @Test
    @DisplayName("assert timeout 구문에 대한 문법")
    void assert_test_timeout() {
        assertTimeout(Duration.ofMillis(100), () ->{
            new Study(10);
            Thread.sleep(300);
        });
    }

    @Test
    @DisplayName("assertThat 으로도 사용이 가능함")
    @Disabled
    void create_create_day() {
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

}