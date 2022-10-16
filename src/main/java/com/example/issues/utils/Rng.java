package com.example.issues.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@PropertySource("classpath:application.properties")
@Component
@RequiredArgsConstructor
@Getter
@Slf4j
@Scope("singleton")
public class Rng {

    private Random random;
    private ConfigurableEnvironment env;

    @Autowired
    public Rng(@Value("${random.seed}") String seed, ConfigurableEnvironment env) {
        this.random = new Random((Long.parseLong(env.getProperty("random.seed"))));
    }
    public String next() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((byte) random.nextInt());
            byte[] digest = md.digest();
            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

        } catch (Exception E) {
            log.warn(E.getMessage());
        }
        return null;
    }

}
