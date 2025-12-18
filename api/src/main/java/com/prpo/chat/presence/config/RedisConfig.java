package com.prpo.chat.presence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.prpo.chat.presence.repository")
public class RedisConfig {

}
