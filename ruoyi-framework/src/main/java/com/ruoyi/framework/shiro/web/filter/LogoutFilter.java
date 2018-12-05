package com.ruoyi.framework.shiro.web.filter;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.util.MessageUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 退出过滤器
 *
 * @author ruoyi
 */
@Slf4j
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

    /**
     * 退出后重定向的地址
     */
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response){
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            SysUser user = ShiroUtils.getSysUser();
            if (StringUtils.isNotNull(user)) {
                String loginName = user.getLoginName();
                // 记录用户退出日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
            }
            // 退出登录
            subject.logout();
            issueRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            log.error("Encountered session exception during logout.  This can generally safely be ignored." , e);
        }
        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url)) {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
