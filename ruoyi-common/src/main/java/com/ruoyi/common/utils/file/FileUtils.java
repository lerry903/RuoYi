package com.ruoyi.common.utils.file;

import com.ruoyi.common.support.CharsetKit;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
@Slf4j
public class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 将文件转为OutputStream
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @throws IOException 异常
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            try(FileInputStream fis =  new FileInputStream(file)){
                byte[] b = new byte[1024];
                int length;
                while ((length = fis.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return 结果
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = true;
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("删除文件失败!", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename){
        return filename.matches(FILENAME_PATTERN);
    }

    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, CharsetKit.UTF8);
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, CharsetKit.UTF8);
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, CharsetKit.UTF8);
        }
        return filename;
    }
}
