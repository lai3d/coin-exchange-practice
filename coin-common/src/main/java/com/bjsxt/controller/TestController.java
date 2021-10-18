package com.bjsxt.controller;

import com.bjsxt.model.R;
import com.bjsxt.model.WebLog;
import com.bjsxt.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = "coin-common 里面测试的接口")
public class TestController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    @Autowired
    private TestService testService;

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1", dataType= "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType= "String", paramType = "query", example = "paramValue")
    })
    public R<String> testMethod(String param, String param1) {
        return R.ok("ok");
    }

    @GetMapping("/common/date")
    @ApiOperation(value = "日志格式化测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testMethod() {
        return R.ok(new Date());
    }

    @GetMapping("/redis/test")
    @ApiOperation(value = "redis测试",authorizations = {@Authorization("Authorization")})
    public R<String> testRedis(){
        WebLog webLog = new WebLog();
        webLog.setResult("ok");
        webLog.setMethod("com.bjsxt.domain.WebLog.testRedis");
        webLog.setUsername("1110");
        redisTemplate.opsForValue().set("com.bjsxt.domain.WebLog", webLog);
        return R.ok("OK");
    }

    @GetMapping("/jetcache/test")
    @ApiOperation(value = "jetcache缓存的测试",authorizations = {@Authorization("Authorization")})
    public R<String> testJetCache(String username){
        WebLog webLog = testService.get(username);
        System.out.println(webLog);
        return R.ok("OK") ; //
    }
}
