package com.romantupikov.simpleapp.security.annotation;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Documented
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Sha {

    @Nonbinding
    SHAAlgorithm algorithm() default SHAAlgorithm.SHA512;

    enum SHAAlgorithm {
        SHA256("SHA-256"),
        SHA512("SHA-512");

        private final String name;

        private SHAAlgorithm(String name) {
            this.name = name;
        }

        public String getAlgorithmName() {
            return this.name;
        }
    }
}
