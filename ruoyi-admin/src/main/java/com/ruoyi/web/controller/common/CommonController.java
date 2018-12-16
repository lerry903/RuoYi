package com.ruoyi.web.controller.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUtils;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
@Api(value = "通用请求处理Controller",tags = {"通用请求处理"})
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    private static final String ENC = "utf-8";

    @RequestMapping("common/download")
    @ApiOperation(value = "通用下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName",value = "文件名",required = true),
            @ApiImplicitParam(name = "delete",value = "是否删除临时文件",required = true,dataType ="boolean")
    })
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf('_') + 1);
        try {
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding(ENC);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    private String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, ENC);
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, ENC);
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, ENC);
        }
        return filename;
    }
}
