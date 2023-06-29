package com.keyon.sfilehub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisPersistentRe implements PersistentTokenRepository {
    private final static String USERNAME_KEY = "spring:security:rememberMe:USERNAME_KEY:";
    private final static String SERIES_KEY = "spring:security:rememberMe:SERIES:";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String series = token.getSeries();
        String key = SERIES_KEY + series;
        String usernameKey = USERNAME_KEY + token.getUsername();
        deleteIfPresent(usernameKey);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", token.getUsername());
        hashMap.put("token", token.getTokenValue());
        hashMap.put("date", String.valueOf(token.getDate().getTime()));
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, hashMap);
//        redisTemplate.expire(key, 7, TimeUnit.DAYS); // 免登录时间为7天
        redisTemplate.expire(key, 60*5, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(usernameKey, series, 60*5, TimeUnit.SECONDS);
    }

    private void deleteIfPresent(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            String series = SERIES_KEY + stringRedisTemplate.opsForValue().get(key);
            if (redisTemplate.hasKey(series)) {
                redisTemplate.delete(series);
                stringRedisTemplate.delete(key);
            }
        }
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = SERIES_KEY + series;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForHash().put(key, "token", tokenValue);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = SERIES_KEY + seriesId;
        List<String> hashKeys = new ArrayList<>();
        hashKeys.add("username");
        hashKeys.add("token");
        hashKeys.add("date");
        List<String> hashValues = redisTemplate.opsForHash().multiGet(key, hashKeys);
        String username = hashValues.get(0);
        String token = hashValues.get(1);
        String date = hashValues.get(2);
        if (username == null || token == null || date == null) {
            return null;
        }
        return new PersistentRememberMeToken(username, seriesId, token, new Date(Long.parseLong(date)));
    }

    @Override
    public void removeUserTokens(String username) {
        String key = USERNAME_KEY + username;
        deleteIfPresent(key);
    }
}
