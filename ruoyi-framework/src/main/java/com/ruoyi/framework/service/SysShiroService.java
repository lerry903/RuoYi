package com.ruoyi.framework.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.system.domain.SysUserOnline;
import com.ruoyi.system.service.ISysUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 会话db操作处理
 * @author LErry.li
 * Date: 2018-12-28
 * Time: 12:48
 */
@Component
public class SysShiroService {

    private final ISysUserOnlineService onlineService;

    @Autowired
    public SysShiroService(ISysUserOnlineService onlineService) {
        this.onlineService = onlineService;
    }

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession){
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId){
        SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return ObjectUtil.isNull(userOnline) ? null : createSession(userOnline);
    }

    private Session createSession(SysUserOnline userOnline){
        OnlineSession onlineSession = new OnlineSession();
        if (ObjectUtil.isNotNull(userOnline)){
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpaddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
