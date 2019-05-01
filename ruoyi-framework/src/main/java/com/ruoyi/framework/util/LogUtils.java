package com.ruoyi.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.utils.IpUtils;

/**
 * 处理并记录日志文件
 *
 * @author ruoyi
 */
public class LogUtils {

    private static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");

    private LogUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 记录访问日志 [username][jsessionid][ip][accept][UserAgent][url][params][Referer]
     *
     * @param request
     * @throws Exception
     */
    public static void logAccess(HttpServletRequest request) throws Exception {
        String username = getUsername();
        String ip = IpUtils.getIpAddr(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader("User-Agent");
        String url = request.getRequestURI();
        String params = getParams(request);

        String s = getBlock(username) +
                getBlock(request.getRequestedSessionId()) +
                getBlock(ip) +
                getBlock(accept) +
                getBlock(userAgent) +
                getBlock(url) +
                getBlock(params) +
                getBlock(request.getHeader("Referer"));
        getAccessLog().info(s);
    }

    /**
     * 记录异常错误 格式 [exception]
     *
     * @param message
     * @param e
     */
    public static void logError(String message, Throwable e) {
        String username = getUsername();
        String s = getBlock("exception") +
                getBlock(username) +
                getBlock(message);
        getErrorLog().error(s, e);
    }

    /**
     * 记录页面错误 错误日志记录 [page/eception][username][statusCode][errorMessage][servletName][uri][exceptionName][ip][exception]
     *
     * @param request
     */
    public static void logPageError(HttpServletRequest request) {
        String username = getUsername();

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (statusCode == null) {
            statusCode = 0;
        }

        StringBuilder s = new StringBuilder();
        s.append(getBlock(t == null ? "page" : "exception"));
        s.append(getBlock(username));
        s.append(getBlock(statusCode));
        s.append(getBlock(message));
        s.append(getBlock(IpUtils.getIpAddr(request)));

        s.append(getBlock(uri));
        s.append(getBlock(request.getHeader("Referer")));
        StringWriter sw = new StringWriter();

        while (t != null) {
            t.printStackTrace(new PrintWriter(sw));
            t = t.getCause();
        }
        s.append(getBlock(sw.toString()));
        String msg = s.toString();
        getErrorLog().error(msg);

    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "" ;
        }
        return "[" + msg.toString() + "]" ;
    }

    protected static String getParams(HttpServletRequest request) throws Exception {
        Map<String, String[]> params = request.getParameterMap();
        return JSON.marshal(params);
    }

    protected static String getUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static Logger getAccessLog() {
        return ACCESS_LOG;
    }

    public static Logger getErrorLog() {
        return ERROR_LOG;
    }
}
