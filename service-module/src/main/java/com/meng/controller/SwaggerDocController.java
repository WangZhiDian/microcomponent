package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.bean.DataTransObj;
import com.meng.bean.SwaggerBeanObj;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller 可以使用swagger注解，在服务启动后，通过端口访问到接口文档。
 @ApiOperation :
对当前具体接口的描述， value 即标题， httpMethod 请求类型

@ApiImplicitParams：
接口的入参，内部是一个数组的形式

@ApiImplicitParam：
上面注解内单个入参的声明方式，name 参数名， value 对参数的说明，required 该参数是否必须， dataType 数据类型 大小写不唯一， paramType 参数类型

@ApiResponses：
接口响应说明

@ApiResponse
上面单个响应的声明方式，code 响应code（注意并不是HTTP请求code而是自己定义的返回code），message 返回信息， response 返回的类型，也可以说响应的类型（这里Response 是自己定义的一种响应类， 基本包括 code, message, data ）

如果只有一个入参或者响应也可以不用 @ApiImplicitParams 或 @ApiResponses
可以直接使用 @ApiImplicitParam 以及 @ApiResponse 一般都使用复数形式使得保持一致

swagger的接口查看地址：
http://localhost:8080/swagger-ui.html#/
 *
 */
//@Api 用在总的 controller 上方value 即名称，也可以默认不写value ，默认会给与 value
//tags 即对 api 的分类，类似于接口的标签用于进一步分类
@Api(value = "api-title", tags = "api-tag01", description = "des")
@Slf4j
@RestController
public class SwaggerDocController {

    @ApiOperation(value = "title", httpMethod = "GET", consumes = "application/json", produces = "application/json",
            tags = "tag01", notes = "note01")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "唯一标识", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "value", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({
            @ApiResponse(code = 2000, message = "success", response = ApiResult.class),
            @ApiResponse(code = 2004, message = "auth Fail", response = ApiResult.class)}
            )
    @GetMapping(value = "/swagger/get")
    public ApiResult<Object> deal(@RequestParam("uid") String uid,
                                  @RequestParam("value") Long value) {


        return ApiResult.success("suc");
    }


    @GetMapping(value = "/swagger/post")
    public DataTransObj dealGetSave(@RequestBody DataTransObj dataTransObj) {
        return dataTransObj;
    }

    @ApiOperation(value = "title-post", httpMethod = "POST", consumes = "application/json", produces = "application/json",
            tags = "tag02", notes = "note02")
    @PostMapping(value = "/swagger/post")
    public ApiResult<SwaggerBeanObj> dealPostPost(@RequestBody SwaggerBeanObj swaggerBeanObj) {

        return new ApiResult().success2(swaggerBeanObj);
    }

}
