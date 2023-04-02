package com.meng.aspectj;

import com.meng.annotation.PermissionRole;
import com.meng.exception.code.ExceptionCode;
import com.meng.exception.except.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义权限拦截器，将权限字符串放到当前请求中以便用于多个角色匹配符合要求的权限
 * 该方式适用于aop来统一拦截rest的注解
 * @author ruoyi
 */
@Slf4j
@Aspect
@Component
public class PermissionsAspect
{
    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(permissionRole)")
    public void pointCut(JoinPoint joinPoint, PermissionRole permissionRole) throws ServiceException {
        //HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //request.getAttribute();
        // getArgs() 方法获取的是uri上的参数的值，以及uri问好后面的值；
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
                break;
            }
        }
        /*
        if (null == request) {
            log.error("auth handler error : target:[] no param : HttpServletRequest");
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), ExceptionCode.EXCEPT_AUTH_NO_REQUEST);
        }
        */
        // 可以通过rest的uri做map缓存，这样每个uri都可以提前加载，也可以使用配置或者数据库提前
        // 将uri和权限的对应映射上，提前加载，用来对比
        String uri1 = request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern").toString();
        String uri = request.getRequestURI();
        log.info("uri:{}", uri);
        log.info("url:{}", request.getPathInfo() + "|" + request.getServletPath()
            + " |" + request.getContextPath());
        if (null != permissionRole) {
            String roles = permissionRole.roles();
            String requestRole = request.getHeader("role");
            if (!roles.contains(requestRole)) {
                throw new ServiceException(HttpStatus.FORBIDDEN.value(), ExceptionCode.EXCEPT_AUTH_NO_REQUEST);
            }

            // 也可以通过request获取数据，验证登录权限验证开启
            //Object userId = request.getSession().getAttribute("uid");
            /*Object userId = request.getParameter("uid");
            if ("duanxz".equals(userId)) {
                System.out.println("账号正确，成功登录");
            } else {
                System.out.println("账号不正确，需要重新登录");
            }*/
        }

    }



}
