package com.romantupikov.simpleapp.security.control;

import com.romantupikov.simpleapp.security.annotation.HashServiceType;
import com.romantupikov.simpleapp.security.annotation.Sha;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;

public class AlgorithmProducer {

    @Produces
    @HashServiceType(HashServiceType.HashType.SHA)
    @Sha
    public HashGenerator produceHashGenerator(InjectionPoint ip) {
        HashGenerator hashGenerator = null;

        for (Annotation annotation : ip.getAnnotated().getAnnotations()) {
            if (annotation instanceof Sha) {

                Sha shaAnnotation = (Sha) annotation;
                hashGenerator = new ShaGenerator(shaAnnotation.algorithm().getAlgorithmName());
            }
        }

        return hashGenerator;
    }
}
