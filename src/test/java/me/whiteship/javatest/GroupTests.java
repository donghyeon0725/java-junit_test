package me.whiteship.javatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * > 그룹화 해서, 해당 그룹만 테스트를 하는 방법에는 2가지가 있다.
 *
 * 1. @Tag 어노테이션의 사용
 * 2. 커스텀 어노테이션을 사용하는 경우(내부적으로 @Tag를 사용해야함)
 *
 * 주의 할 점은, 커스텀 태그와 @Tag를 동시에 사용할 때, @Tag의 속성 값이 같다면 그룹화 함수를 실행할 때 지정이 되지 않는 경우가 있음
 * 여기로 예를 들자면, GroupTests 클래스 내부에 @FastTest 어노테이션을 가진 메소드와 @Tag("fast") 어노테이션을 가진 메소드가 둘다 있을 경우 그룹 테스트가 안될 수 있음
 *
 *
 * > 그룹으로 실행하는 방법
 *
 * 1. junit 실행 시 test kind를 Tag로 하는 방법 => Tag expression 를 해당 속성으로 변경해줌
 * 2. mvn test 를 이용해서 하는 방법 => pom.xml에 빌드 옵션 추가 필요 + "mvn test -P 빌드옵션id"
 *      => 또는 test 시, profile 옵션 값으로 id를 주면 됨
 * */
/*
pom.xml에 아래와 같이 명시해주면 된다.

<profiles>
    <profile>
        <id>default</id><!-- 빌드 옵션의 아이디 -->
        <activation>
            <!-- 기본 적으로 활성화 되어 있는 빌드 옵션 -->
            <activeByDefault>true</activeByDefault>
        </activation>
        <build>
            <plugins>

                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <!-- group 이라는 속성의 태그를 선택한다. -->
                        <groups>group</groups>
                    </configuration>
                </plugin>
            </plugins>

        </build>
    </profile>
</profiles>

*/
class GroupTests {
    @Test
    @DisplayName("테스트를 그룹화하는 방법")
    @Tag("group") // 태그로 상황에 따라 실행 될 함수를 다르게 만들고 싶을 때  => 실행 환경 설정에 가서 Test Kind를 Tags로 바꾸고 을 fast로 하면 실행 됨
    void group() {
        System.out.println("group 이라는 이름으로 그룹화 되어있다.");
    }

    @Test
    @DisplayName("slow 그룹 테스트")
    @SlowGroup
    void slow_group() {
        System.out.println("slow group");
    }

    @Test
    @FastGroup
    @DisplayName("fast 그룹 테스트")
    void fast_group() {
        System.out.println("fast group");
    }
}