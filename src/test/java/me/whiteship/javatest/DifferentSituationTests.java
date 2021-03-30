package me.whiteship.javatest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @EnabledOnOs => os를 조건으로 걸고 싶을 때 (중괄호를 이용해서 복수 선택 가능)
 * @EnabledOnJre => 자바 버전을 조건으로 걸고 싶을 때
 * @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL") => 환경 변수 조건을 어노테이션으로 주고 싶을 때 사용
 * */
public class DifferentSituationTests {


    @Test
    @DisplayName("테스트에 조건을 걸고 싶은 경우")
    void case_env_val() {
        /**
         * System.getenv
         * 환경 변수를 가져온다.
         * */
        String test_env = System.getenv("TEST_ENV");
        System.out.println("TEST_ENV 환경 변수 값은? " + test_env);
        // 제이유닛 주피터에서 제공 => 환경이 맞는지 확인

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("local");
        });

        assumingThat("network".equalsIgnoreCase(test_env), () -> {
            System.out.println("network");
        });

        // 무조건 실행되는 로직
        assumingThat("test".equalsIgnoreCase("test"), () -> assertNotNull(null));
    }

    @Test
    @DisplayName("OS 가 맥과 또는 리눅스인 경우에 실행")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void case_mac_and_linux() {
        System.out.println("맥과 리눅스에서만 실행");
    }

    @Test
    @DisplayName("OS 가 윈도우인 경우에 실행")
    @EnabledOnOs(OS.WINDOWS)
    void case_window() {
        System.out.println("윈도우에서만 실행");
    }

    @Test
    @DisplayName("자바 버전이 맞는 경우에 실행")
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    void case_jre_version() {
        System.out.println("자바 버전이 맞는 경우에 실행");
    }

    @Test
    @DisplayName("환경 변수가 맞는지")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void case_env_val_with_annotation() {
        System.out.println("환경 변수가 다르면 실행이 안 됩니다.");
    }

    @Test
    @EnabledIfSystemProperty(named = "TEST_ENV", matches = "LOCAL")
    void case_system_val_with_annotation() {
        System.out.println("시스템 변수가 다르면 실행이 안 됩니다.");
    }
}
