package com.ruoyi.web.controller.monitor;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.OnlineStatus;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.framework.shiro.session.OnlineSessionDAO;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.system.domain.SysUserOnline;
import com.ruoyi.system.service.impl.SysUserOnlineServiceImpl;
import com.ruoyi.framework.web.base.BaseController;

/**
 * 在线用户监控
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    private final SysUserOnlineServiceImpl userOnlineService;

    private final OnlineSessionDAO onlineSessionDAO;

    @Autowired
    public SysUserOnlineController(SysUserOnlineServiceImpl userOnlineService, OnlineSessionDAO onlineSessionDAO) {
        this.userOnlineService = userOnlineService;
        this.onlineSessionDAO = onlineSessionDAO;
    }

    @RequiresPermissions("monitor:online:view")
    @GetMapping()
    public String online() {
        String prefix = "monitor/online";
        return prefix + "/online";
    }

    @RequiresPermissions("monitor:online:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUserOnline userOnline) {
        startPage();
        List<SysUserOnline> list = userOnlineService.selectUserOnlineList(userOnline);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:online:batchForceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @PostMapping("/batchForceLogout")
    @ResponseBody
    public AjaxResult batchForceLogout(@RequestParam("ids[]") String[] ids) {
        for (String sessionId : ids) {
            String message = logout(sessionId);
            if (StringUtils.isNotEmpty(message)) {
                return error(message);
            }
        }
        return success();
    }

    @RequiresPermissions("monitor:online:forceLogout")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @PostMapping("/forceLogout")
    @ResponseBody
    public AjaxResult forceLogout(String sessionId) {
        String message = logout(sessionId);
        if (StringUtils.isNotEmpty(message)) {
            return error(message);
        }
        return success();
    }

    private String logout(String sessionId) {
        SysUserOnline online = userOnlineService.selectOnlineById(sessionId);
        if (sessionId.equals(ShiroUtils.getSessionId())) {
            return "当前登陆用户无法强退";
        }
        if (online == null) {
            return "用户已下线";
        }
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
        if (onlineSession == null) {
            return "用户已下线";
        }
        onlineSession.setStatus(OnlineStatus.off_line);
        online.setStatus(OnlineStatus.off_line);
        userOnlineService.saveOnline(online);
        return null;
    }
}
