package me.whiteship.javatest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 아래 어노테이션이 필요하다.
 * @Test
 * @Tag("fast")
 * @Target(ElementType.METHOD)
 * @Retention(RetentionPolicy.RUNTIME)
 * */
@Test
@Tag("fast")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FastGroup {
}
