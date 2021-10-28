package com.xichuan.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 请求地址中不包含/admin/的，不是控台请求，不需要拦截
        if (!path.contains("/")) {
            return chain.filter(exchange);
        }
        if (path.contains("/system/user/login")
                || path.contains("/system/user/logout")
                || path.contains("/file/f/course/")
                || path.contains("/file/f/teacher/")
                || path.contains("/system/kaptcha")) {
            logger.info("不需要控台登录验证：{}", path);
            return chain.filter(exchange);
        }
        //获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        logger.info("控台登录验证开始，token：{}", token);
        if (token == null || token.isEmpty()) {
            logger.info( "token为空，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        Object object = redisTemplate.opsForValue().get(token);
        if (object == null) {
            logger.warn( "token无效，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } else {
            logger.info("已登录：{}", object);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder()
    {
        return 1;
    }

}