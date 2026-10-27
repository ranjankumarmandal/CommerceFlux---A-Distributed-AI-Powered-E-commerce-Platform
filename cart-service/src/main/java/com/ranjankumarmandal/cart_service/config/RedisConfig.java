package com.ranjankumarmandal.cart_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host:localhost}")
	private String redisHost;

	@Value("${spring.redis.port:6379}")
	private int redisPort;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHost);
		configuration.setPort(redisPort);
		return new LettuceConnectionFactory(configuration);
	}

	@Bean
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHost);
		configuration.setPort(redisPort);
		return new org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory(configuration);
	}

	@Bean
	public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
		RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
				.<String, Object>newSerializationContext()
				.key(new StringRedisSerializer())
				.value(new GenericJackson2JsonRedisSerializer())
				.hashKey(new StringRedisSerializer())
				.hashValue(new GenericJackson2JsonRedisSerializer())
				.build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}
}
