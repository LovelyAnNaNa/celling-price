package com.whtt.cellingprice.common;

import com.whtt.cellingprice.entity.pojo.SysUser;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@WebFilter(filterName = "login")
public class LoginRequiredInterceptor implements HandlerInterceptor  {
    protected org.mybatis.logging.Logger logger = LoggerFactory.getLogger(getClass());

    Logger log = Logger.getLogger(String.valueOf(LoginRequiredInterceptor.class));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
//		log.info("basePath:"+basePath);
//		log.info("path:"+path);

        if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
            return true;
        }



        //如果登录了，会把用户信息存进session
        HttpSession session = request.getSession();

        List<SysUser> users =  (List<SysUser>) session.getAttribute("userList");
        if(users==null){
			/*log.info("尚未登录，跳转到登录界面");
			response.sendRedirect(request.getContextPath()+"signin");*/

            String requestType = request.getHeader("X-Requested-With");
//			System.out.println(requestType);
            if(requestType!=null && requestType.equals("XMLHttpRequest")){
                response.setHeader("sessionstatus","timeout");
//				response.setHeader("basePath",request.getContextPath());
                response.getWriter().print("LoginTimeout");
                return false;
            } else {
                log.info("尚未登录，跳转到登录界面");
                response.sendRedirect(request.getContextPath()+"signin");
            }
            return false;
        }
//		log.info("用户已登录,userName:"+userInfo.getSysUser().getUserName());
        return true;
    }

    /**
     * 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath){
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        //notLoginPaths.add("/");
        notLoginPaths.add("/index");
        notLoginPaths.add("/login");
        notLoginPaths.add("/register");
        //notLoginPaths.add("/sys/logout");
        //notLoginPaths.add("/loginTimeout");

        if(notLoginPaths.contains(path)) return false;
        return true;
    }



}


