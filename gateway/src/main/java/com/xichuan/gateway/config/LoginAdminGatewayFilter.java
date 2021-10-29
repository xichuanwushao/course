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


            // 增加权限校验，gateway里没有LoginUserDto，所以全部用JSON操作
            logger.info("接口权限校验，请求地址：{}", path);
            boolean exist = false;
            JSONObject loginUserDto = JSON.parseObject(String.valueOf(object));
            JSONArray requests = loginUserDto.getJSONArray("requests");
            // 遍历所有【权限请求】，判断当前请求的地址是否在【权限请求】里
            for (int i = 0, l = requests.size(); i < l; i++) {
                String request = (String) requests.get(i);
                if (path.contains(request)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                logger.info("权限校验通过");
            } else {
                logger.warn("权限校验未通过");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder()
    {
        return 1;
    }

}
