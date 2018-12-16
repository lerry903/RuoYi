package com.ruoyi.web.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.framework.web.domain.Server;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/server")
@ApiIgnore(value = "服务器监控")
public class ServerController extends BaseController {

    @RequiresPermissions("monitor:server:view")
    @GetMapping()
    public String server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        String prefix = "monitor/server";
        return prefix + "/server";
    }
}
