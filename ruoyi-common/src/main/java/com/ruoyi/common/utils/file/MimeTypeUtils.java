package com.ruoyi.common.utils.file;

/**
 * 媒体类型工具类
 *
 * @author ruoyi
 */
class MimeTypeUtils {
    static final String IMAGE_PNG = "image/png";

    static final String IMAGE_JPG = "image/jpg";

    static final String IMAGE_JPEG = "image/jpeg";

    static final String IMAGE_BMP = "image/bmp";

    static final String IMAGE_GIF = "image/gif";

    static final String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};

    static final String[] FLASH_EXTENSION = {"swf", "flv"};

    static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb"};

    static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf"};

    static String getExtension(String prefix) {
        switch (prefix) {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }
}
