package com.ruoyi.web.controller.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.page.PageDomain;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.framework.web.page.TableSupport;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 表格相关
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/table")
public class DemoTableController extends BaseController {
    private String prefix = "demo/table";

    private final static List<UserTableModel> USERS = new ArrayList<>();

    static {
        USERS.add(new UserTableModel(1, "1000001", "测试1", "0", "15888888888", "ry@qq.com", 150.0, "0"));
        USERS.add(new UserTableModel(2, "1000002", "测试2", "1", "15666666666", "ry@qq.com", 180.0, "1"));
        USERS.add(new UserTableModel(3, "1000003", "测试3", "0", "15666666666", "ry@qq.com", 110.0, "1"));
        USERS.add(new UserTableModel(4, "1000004", "测试4", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        USERS.add(new UserTableModel(5, "1000005", "测试5", "0", "15666666666", "ry@qq.com", 140.0, "1"));
        USERS.add(new UserTableModel(6, "1000006", "测试6", "1", "15666666666", "ry@qq.com", 330.0, "1"));
        USERS.add(new UserTableModel(7, "1000007", "测试7", "0", "15666666666", "ry@qq.com", 160.0, "1"));
        USERS.add(new UserTableModel(8, "1000008", "测试8", "1", "15666666666", "ry@qq.com", 170.0, "1"));
        USERS.add(new UserTableModel(9, "1000009", "测试9", "0", "15666666666", "ry@qq.com", 180.0, "1"));
        USERS.add(new UserTableModel(10, "1000010", "测试10", "0", "15666666666", "ry@qq.com", 210.0, "1"));
        USERS.add(new UserTableModel(11, "1000011", "测试11", "1", "15666666666", "ry@qq.com", 110.0, "1"));
        USERS.add(new UserTableModel(12, "1000012", "测试12", "0", "15666666666", "ry@qq.com", 120.0, "1"));
        USERS.add(new UserTableModel(13, "1000013", "测试13", "1", "15666666666", "ry@qq.com", 380.0, "1"));
        USERS.add(new UserTableModel(14, "1000014", "测试14", "0", "15666666666", "ry@qq.com", 280.0, "1"));
        USERS.add(new UserTableModel(15, "1000015", "测试15", "0", "15666666666", "ry@qq.com", 570.0, "1"));
        USERS.add(new UserTableModel(16, "1000016", "测试16", "1", "15666666666", "ry@qq.com", 260.0, "1"));
        USERS.add(new UserTableModel(17, "1000017", "测试17", "1", "15666666666", "ry@qq.com", 210.0, "1"));
        USERS.add(new UserTableModel(18, "1000018", "测试18", "1", "15666666666", "ry@qq.com", 340.0, "1"));
        USERS.add(new UserTableModel(19, "1000019", "测试19", "1", "15666666666", "ry@qq.com", 160.0, "1"));
        USERS.add(new UserTableModel(20, "1000020", "测试20", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        USERS.add(new UserTableModel(21, "1000021", "测试21", "1", "15666666666", "ry@qq.com", 120.0, "1"));
        USERS.add(new UserTableModel(22, "1000022", "测试22", "1", "15666666666", "ry@qq.com", 130.0, "1"));
        USERS.add(new UserTableModel(23, "1000023", "测试23", "1", "15666666666", "ry@qq.com", 490.0, "1"));
        USERS.add(new UserTableModel(24, "1000024", "测试24", "1", "15666666666", "ry@qq.com", 570.0, "1"));
        USERS.add(new UserTableModel(25, "1000025", "测试25", "1", "15666666666", "ry@qq.com", 250.0, "1"));
        USERS.add(new UserTableModel(26, "1000026", "测试26", "1", "15666666666", "ry@qq.com", 250.0, "1"));
    }

    /**
     * 搜索相关
     */
    @GetMapping("/search")
    public String search() {
        return prefix + "/search";
    }

    /**
     * 数据汇总
     */
    @GetMapping("/footer")
    public String footer() {
        return prefix + "/footer";
    }

    /**
     * 组合表头
     */
    @GetMapping("/groupHeader")
    public String groupHeader() {
        return prefix + "/groupHeader";
    }

    /**
     * 表格导出
     */
    @GetMapping("/export")
    public String export() {
        return prefix + "/export";
    }

    /**
     * 翻页记住选择
     */
    @GetMapping("/remember")
    public String remember() {
        return prefix + "/remember";
    }

    /**
     * 跳转至指定页
     */
    @GetMapping("/pageGo")
    public String pageGo() {
        return prefix + "/pageGo";
    }

    /**
     * 自定义查询参数
     */
    @GetMapping("/params")
    public String params() {
        return prefix + "/params";
    }

    /**
     * 多表格
     */
    @GetMapping("/multi")
    public String multi() {
        return prefix + "/multi";
    }

    /**
     * 点击按钮加载表格
     */
    @GetMapping("/button")
    public String button() {
        return prefix + "/button";
    }

    /**
     * 表格冻结列
     */
    @GetMapping("/fixedColumns")
    public String fixedColumns() {
        return prefix + "/fixedColumns";
    }

    /**
     * 自定义触发事件
     */
    @GetMapping("/event")
    public String event() {
        return prefix + "/event";
    }

    /**
     * 表格细节视图
     */
    @GetMapping("/detail")
    public String detail() {
        return prefix + "/detail";
    }

    /**
     * 表格图片预览
     */
    @GetMapping("/image")
    public String image() {
        return prefix + "/image";
    }

    /**
     * 动态增删改查
     */
    @GetMapping("/curd")
    public String curd() {
        return prefix + "/curd";
    }

    /**
     * 表格拖拽操作
     */
    @GetMapping("/reorder")
    public String reorder(){
        return prefix + "/reorder";
    }


    /**
     * 表格其他操作
     */
    @GetMapping("/other")
    public String other() {
        return prefix + "/other";
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserTableModel userModel) {
        TableDataInfo rspData = new TableDataInfo();
        List<UserTableModel> userList = new ArrayList<>(Arrays.asList(new UserTableModel[USERS.size()]));
        Collections.copy(userList, USERS);
        // 查询条件过滤
        if (StrUtil.isNotEmpty(userModel.getUserName())) {
            userList.clear();
            for (UserTableModel user : USERS) {
                if (user.getUserName().equals(userModel.getUserName())) {
                    userList.add(user);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize()) {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        int pageNum = (pageDomain.getPageNum() - 1) * 10;
        int pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size()) {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }
}

@Data
class UserTableModel {
    /**
     * 用户ID
     */
    private int userId;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户余额
     */
    private double userBalance;

    /**
     * 用户状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public UserTableModel() {

    }

    public UserTableModel(int userId, String userCode, String userName, String userSex, String userPhone,
                          String userEmail, double userBalance, String status) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtil.date();
    }

}
