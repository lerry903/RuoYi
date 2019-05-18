package com.ruoyi.generator.util;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.generator.domain.ColumnInfo;
import com.ruoyi.generator.domain.TableInfo;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器 工具类
 *
 * @author ruoyi
 */
public class GenUtils {

    private GenUtils(){
        throw new IllegalStateException("Utility class");
    }
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = getProjectPath();

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper" ;

    /**
     * html空间路径
     */
    private static final String TEMPLATES_PATH = "main/resources/templates" ;

    /**
     * 类型转换
     */
    private static Map<String, String> javaTypeMap = new HashMap<>();

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns) {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StrUtil.upperFirst(StrUtil.toUnderlineCase(column.getColumnName()));
            column.setAttrName(attrName);
            column.setAttrname(StrUtil.lowerFirst(attrName));
            column.setExtra(column.getExtra());
            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table) {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = Global.getPackageName();
        velocityContext.put("tableName" , table.getTableName());
        velocityContext.put("tableComment" , replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey" , table.getPrimaryKey());
        velocityContext.put("className" , table.getClassName());
        velocityContext.put("classname" , table.getClassname());
        velocityContext.put("moduleName" , getModuleName(packageName));
        velocityContext.put("columns" , table.getColumns());
        velocityContext.put("basePackage" , getBasePackage(packageName));
        velocityContext.put("package" , packageName);
        velocityContext.put("author" , Global.getAuthor());
        velocityContext.put("datetime" , DateUtil.today());
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/Mapper.java.vm");
        templates.add("vm/java/Service.java.vm");
        templates.add("vm/java/ServiceImpl.java.vm");
        templates.add("vm/java/Controller.java.vm");
        templates.add("vm/xml/Mapper.xml.vm");
        templates.add("vm/html/list.html.vm");
        templates.add("vm/html/add.html.vm");
        templates.add("vm/html/edit.html.vm");
        templates.add("vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        if (Constants.AUTO_REOMVE_PRE.equals(Global.getAutoRemovePre())) {
            tableName = tableName.substring(tableName.indexOf('_') + 1);
        }
        if (StrUtil.isNotEmpty(Global.getTablePrefix())) {
            tableName = tableName.replace(Global.getTablePrefix(), "");
        }
        return StrUtil.upperFirst(StrUtil.toUnderlineCase(tableName));
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName) {
        String str = "/";
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH;
        String mybatisPath = MYBATIS_PATH + str + moduleName + str + className;
        String htmlPath = TEMPLATES_PATH + str + moduleName + str + classname;

        if (template.contains("domain.java.vm")) {
            return javaPath + "domain" + "/" + className + ".java" ;
        }

        if (template.contains("Mapper.java.vm")) {
            return javaPath + "mapper" + "/" + className + "Mapper.java" ;
        }

        if (template.contains("Service.java.vm")) {
            return javaPath + "service" + "/" + "I" + className + "Service.java" ;
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + "service" + "/impl/" + className + "ServiceImpl.java" ;
        }

        if (template.contains("Controller.java.vm")) {
            return javaPath + "controller" + "/" + className + "Controller.java" ;
        }

        if (template.contains("Mapper.xml.vm")) {
            return mybatisPath + "Mapper.xml" ;
        }

        if (template.contains("list.html.vm")) {
            return htmlPath + "/" + classname + ".html" ;
        }
        if (template.contains("add.html.vm")) {
            return htmlPath + "/" + "add.html" ;
        }
        if (template.contains("edit.html.vm")) {
            return htmlPath + "/" + "edit.html" ;
        }
        if (template.contains("sql.vm")) {
            return classname + "Menu.sql" ;
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf('.');
        int nameLength = packageName.length();
        return StrUtil.sub(packageName, lastIndex + 1, nameLength);
    }

    private static String getBasePackage(String packageName) {
        int lastIndex = packageName.lastIndexOf('.');
        return StrUtil.sub(packageName, 0, lastIndex);
    }

    private static String getProjectPath() {
        String packageName = Global.getPackageName();
        StringBuilder projectPath = new StringBuilder();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace("." , "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    private static String replaceKeyword(String keyword) {
        return keyword.replaceAll("(?:表|信息|管理)", "");
    }

    static {
        String string = "String";
        String integer = "Integer";
        String date = "Date";
        javaTypeMap.put("tinyint" , integer);
        javaTypeMap.put("smallint" , integer);
        javaTypeMap.put("mediumint" , integer);
        javaTypeMap.put("int" , integer);
        javaTypeMap.put("number", integer);
        javaTypeMap.put("integer" , "integer");
        javaTypeMap.put("bigint" , "Long");
        javaTypeMap.put("float" , "Float");
        javaTypeMap.put("double" , "Double");
        javaTypeMap.put("decimal" , "BigDecimal");
        javaTypeMap.put("bit" , "Boolean");
        javaTypeMap.put("char" , string);
        javaTypeMap.put("varchar" , string);
        javaTypeMap.put("varchar2", string);
        javaTypeMap.put("tinytext" , string);
        javaTypeMap.put("text" , string);
        javaTypeMap.put("mediumtext" , string);
        javaTypeMap.put("longtext" , string);
        javaTypeMap.put("time" , date);
        javaTypeMap.put("date" , date);
        javaTypeMap.put("datetime" , date);
        javaTypeMap.put("timestamp" , date);
    }
}
