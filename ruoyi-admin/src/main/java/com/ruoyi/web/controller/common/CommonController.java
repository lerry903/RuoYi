package com.ruoyi.web.controller.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 文件上传路径
     */
    private static final String UPLOAD_PATH = "/profile/upload/";

    private final ServerConfig serverConfig;

    @Autowired
    public CommonController(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @RequestMapping("common/download")
    @ApiOperation(value = "通用下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName",value = "文件名",required = true),
            @ApiImplicitParam(name = "delete",value = "是否删除临时文件",required = true,dataType ="boolean")
    })
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf('_') + 1);
        try {
            if (!FileUtils.isValidFilename(fileName)){
                throw new BusinessException(String.format(" 文件名称(%s)非法，不允许下载。 ", fileName));
            }
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

    @PostMapping("/common/upload")
    @ResponseBody
    @ApiOperation(value = "通用文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName",value = "文件名",required = true),
            @ApiImplicitParam(name = "delete",value = "是否删除临时文件",required = true,dataType ="boolean")
    })
    public AjaxResult uploadFile(MultipartFile file){
        try
        {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + UPLOAD_PATH + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
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
