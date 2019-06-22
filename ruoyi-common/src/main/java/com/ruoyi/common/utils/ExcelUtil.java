package com.ruoyi.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Excel相关处理
 *
 * @author ruoyi
 */
@Slf4j
public class ExcelUtil<T> {
    /**
     * Excel sheet最大行数，默认65536
     */
    private static final int SHEET_SIZE = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Excel.Type type;

    /**
     * 工作薄对象
     */
    private Workbook workbook;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Field> fields;

    /**
     * 实体对象
     */
    private Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, Excel.Type type){
        if (list == null){
            list = new ArrayList<>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param input 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream input) {
        return importExcel(StrUtil.EMPTY, input);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param input     输入流
     * @return 转换后集合
     */
    private List<T> importExcel(String sheetName, InputStream input) {
        this.type = Excel.Type.IMPORT;
        List<T> list = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(input)) {
            this.workbook = workbook;
            Sheet sheet;
            if (StrUtil.isNotEmpty(sheetName)) {
                // 如果指定sheet名,则取指定sheet中的内容.
                sheet = workbook.getSheet(sheetName);
            } else {
                // 如果传入的sheet名不存在则默认指向第1个sheet.
                sheet = workbook.getSheetAt(0);
            }

            if (sheet == null) {
                throw new IOException("文件sheet不存在");
            }

            int rows = sheet.getPhysicalNumberOfRows();

            if (rows > 0) {
                // 定义一个map用于存放excel列的序号和field.
                Map<String, Integer> cellMap = new HashMap<>();
                //获取表头
                Row heard = sheet.getRow(0);
                for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                    Cell cell = heard.getCell(i);
                    if (ObjectUtil.isNotNull(cell)) {
                        String value = Convert.toStr(this.getCellValue(heard, i));
                        cellMap.put(value, i);
                    } else {
                        cellMap.put(null, i);
                    }
                }

                // 有数据时才处理 得到类的所有field.
                Field[] allFields = clazz.getDeclaredFields();
                // 定义一个map用于存放列的序号和field.
                Map<Integer, Field> fieldsMap = new HashMap<>();
                for (Field field : allFields) {
                    // 将有注解的field存放到map中.
                    Excel attr = field.getAnnotation(Excel.class);
                    boolean annotation = attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type);
                    if(annotation){
                        // 设置类的私有字段属性可访问.
                        field.setAccessible(true);
                        Integer column = cellMap.get(attr.name());
                        fieldsMap.put(column, field);
                    }
                }
                this.getImportData(rows,fieldsMap);
            }
        } catch (InvalidFormatException | IOException | IllegalAccessException | InstantiationException e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    private void getImportData(int rows, Map<Integer, Field> fieldsMap) throws IllegalAccessException, InstantiationException {
        for (int i = 1; i < rows; i++) {
            // 从第2行开始取数据,默认第一行是表头.
            Row row = sheet.getRow(i);
            T entity = null;
            for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
                Object val = this.getCellValue(row, entry.getKey());
                // 如果不存在实例则新建.
                entity = (entity == null ? clazz.newInstance() : entity);
                // 从map中得到对应列的field.
                Field field = fieldsMap.get(entry.getKey());
                // 取得类型,并根据对象类型设置值.
                Class<?> fieldType = field.getType();
                if (String.class == fieldType) {
                    field.set(entity, Convert.toStr(val));
                } else if (Integer.TYPE == fieldType || Integer.class == fieldType) {
                    field.set(entity, Convert.toInt(val));
                } else if (Long.TYPE == fieldType || Long.class == fieldType) {
                    field.set(entity, Convert.toLong(val));
                } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                    field.set(entity, Convert.toFloat(val));
                } else if (Short.TYPE == fieldType || Short.class == fieldType) {
                    field.set(entity, Convert.toShort(val));
                } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                    field.set(entity, Convert.toDouble(val));
                }else if (java.util.Date.class == fieldType) {
                    if (val instanceof String){
                        val = DateUtil.parse(Convert.toStr(val));
                        field.set(entity, val);
                    }else if (val instanceof Double){
                        val = org.apache.poi.ss.usermodel.DateUtil.getJavaDate((Double) val);
                        field.set(entity, val);
                    }
                }else if (BigDecimal.class == fieldType){
                    field.set(entity, Convert.toBigDecimal(val));
                }
                if (ObjectUtil.isNotNull(fieldType)){
                    Excel attr = field.getAnnotation(Excel.class);
                    String propertyName = field.getName();
                    if (StrUtil.isNotEmpty(attr.targetAttr())){
                        propertyName = field.getName() + "." + attr.targetAttr();
                    }else if (StrUtil.isNotEmpty(attr.readConverterExp())){
                        val = reverseByExp(String.valueOf(val), attr.readConverterExp());
                    }
                    ReflectUtil.setFieldValue(entity, propertyName, val);
                }
            }
            if (entity != null) {
                list.add(entity);
            }
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName){
        this.init(list, sheetName, Excel.Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName){
        this.init(null, sheetName, Excel.Type.IMPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public AjaxResult exportExcel() {
        OutputStream out = null;
        // 产生工作薄对象
        try{
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / SHEET_SIZE);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);
                // 产生一行
                Row row = sheet.createRow(0);
                // 写入各个字段的列头名称
                this.setCellTitle(fields, row);
                if (Excel.Type.EXPORT.equals(type)){
                    fillExcelData(index);
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            workbook.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            log.error("导出Excel异常", e);
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
    }

    private void setCellTitle(List<Field> fields, Row row) {
        int excelsNo = 0;
        for (int column = 0; column < fields.size(); column++) {
            Field field = fields.get(column);
            if (field.isAnnotationPresent(Excel.class)){
                Excel excel = field.getAnnotation(Excel.class);
                createCell(excel, row, column);
            }
            if (field.isAnnotationPresent(Excels.class)){
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                // 写入列名
                Excel excel = excels[excelsNo++];
                createCell(excel, row, column);
            }
        }
    }

    /**
     * 填充excel数据
     *  @param index 序号
     *
     */
    private void fillExcelData(int index){
        int startNo = index * SHEET_SIZE;
        int endNo = Math.min(startNo + SHEET_SIZE, list.size());
        // 写入各条记录,每条记录对应excel表中的一行
        CellStyle cs = workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++){
            Row row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = list.get(i);
            int excelsNo = 0;
            for (int column = 0; column < fields.size(); column++){
                // 获得field.
                Field field = fields.get(column);
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                if (field.isAnnotationPresent(Excel.class)){
                    addCell(field.getAnnotation(Excel.class), row, vo, field, column, cs);
                }
                if (field.isAnnotationPresent(Excels.class)){
                    Excels attrs = field.getAnnotation(Excels.class);
                    Excel[] excels = attrs.value();
                    Excel excel = excels[excelsNo++];
                    addCell(excel, row, vo, field, column, cs);
                }
            }
        }
    }

    /**
     * 创建单元格
     */
    private void createCell(Excel attr, Row row, int column){
        // 创建列
        Cell cell = row.createCell(column);
        // 设置列中写入内容为String类型
        cell.setCellType(CellType.STRING);
        // 写入列名
        cell.setCellValue(attr.name());
        CellStyle cellStyle = createStyle(attr, row, column);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 创建表格样式
     */
    private CellStyle createStyle(Excel attr, Row row, int column){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (attr.name().contains("注：")){
            Font font = workbook.createFont();
            font.setColor(HSSFFont.COLOR_RED);
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
            sheet.setColumnWidth(column, 6000);
        }else{
            Font font = workbook.createFont();
            // 粗体显示
            font.setBold(true);
            // 选择需要用到的字体格式
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        // 如果设置了提示信息则鼠标放上去提示.
        if (StrUtil.isNotEmpty(attr.prompt())){
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, attr.prompt(),  column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0){
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(),  column, column);
        }
        return cellStyle;
    }

    /**
     * 添加单元格
     */
    private void addCell(Excel attr, Row row, T vo, Field field, int column, CellStyle cs){
        Cell cell;
        try{
            // 设置行高
            row.setHeight((short) (attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()){
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(cs);

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                if (StrUtil.isNotEmpty(dateFormat) && ObjectUtil.isNotNull(value)){
                    cell.setCellValue(DateUtil.format(((Date) value), dateFormat));
                }else if (StrUtil.isNotEmpty(readConverterExp) && ObjectUtil.isNotNull(value)){
                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                }else{
                    cell.setCellType(CellType.STRING);
                    // 如果数据存在就填入,不存在填入空格.
                    cell.setCellValue(ObjectUtil.isNull(value) ? attr.defaultValue() : value + attr.suffix());
                }
            }
        }catch (Exception e){
            log.error("导出Excel失败%s", e);
        }
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstCol 开始列
     * @param endCol   结束列
     */
    private static void setXSSFValidation(Sheet sheet, String[] textlist, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(1, 100, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation){
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }else{
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     */
    private static String convertByExp(String propertyValue, String converterExp) {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return propertyValue;
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @return 解析后值
     */
    private static String reverseByExp(String propertyValue, String converterExp) {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        } catch (Exception e) {
            log.error("反向解析值失败", e);
            throw e;
        }
        return propertyValue;
    }

    /**
     * 编码文件名
     */
    private String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    private String getAbsoluteFile(String filename) {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            boolean mkdirs = desc.getParentFile().mkdirs();
            if(mkdirs){
                log.error("获取路径失败");
            }
        }
        return downloadPath;
    }

    /**
     * 获取bean中的属性值
     *
     * @param vo 实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object o = field.get(vo);
        if (StrUtil.isNotEmpty(excel.targetAttr())){
            String target = excel.targetAttr();
            if (target.contains(".")){
                String[] targets = target.split("[.]");
                for (String name : targets){
                    o = getValue(o, name);
                }
            }else{
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o 类对象
     * @param name 方法名称
     * @return value
     */
    private Object getValue(Object o, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (StrUtil.isNotEmpty(name)){
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = o.getClass().getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = new ArrayList<>();
        Class<?> tempClass = clazz;
        List<Field> tempFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        while (tempClass != null){
            tempClass = tempClass.getSuperclass();
            if (tempClass != null) {
                tempFields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            }
        }
        putToFields(tempFields);
    }

    /**
     * 放到字段集合中
     */
    private void putToFields(List<Field> fields){
        for (Field field : fields){
            Excel attr = field.getAnnotation(Excel.class);
            boolean flag = attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type);
            if (flag){
                this.fields.add(field);
            }
        }
    }

    /**
     * 创建一个工作簿
     */
    private void createWorkbook() {
        this.workbook = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    private void createSheet(double sheetNo, int index) {
        this.sheet = workbook.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            workbook.setSheetName(index, sheetName);
        } else {
            workbook.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    private Object getCellValue(Row row, int column) {
        if (row == null) {
            return null;
        }
        Object val = "" ;
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // POI Excel 日期格式转换
                        val = org.apache.poi.ss.usermodel.DateUtil.getJavaDate((Double) val);
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }
    /**
     * 设置 POI XSSFSheet 单元格提示
     * @param sheet 表单
     * @param promptContent 提示内容
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    private static void setXSSFPrompt(Sheet sheet, String promptContent, int firstCol, int endCol) {
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = dvHelper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(1, 100, firstCol, endCol);
        DataValidation dataValidation = dvHelper.createValidation(constraint, regions);
        dataValidation.createPromptBox("", promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }
}