package com.example.springboot.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author snail
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {

    /*
       首先，Spring框架会扫描项目中的组件，包括UserServiceImpl类和其他被标注为@Component、@Service、@Repository等注解的类。

       当发现UserServiceImpl类上标注了@Service注解时，Spring框架会将其识别为一个服务类，并创建一个实例对象。

       在其他需要使用IUserService接口的地方，通过使用@Resource注解将userService对象注入进来。

       当需要使用userService对象时，Spring框架会自动将UserServiceImpl的实例对象赋值给userService，完成依赖注入的过程。*/
    @Resource//依赖注入
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO, HttpSession session) {//登录检查
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }//校验
        return Result.success(userService.login(userDTO,session));
    }
    @GetMapping("/validate")
    public Result validate(HttpSession session) {
        String sessionId = session.getId();
        System.out.println("sessionId："+sessionId);
        User one = (User)session.getAttribute(sessionId);
        if (one != null) {
            UserDTO userDTO=new UserDTO();
            BeanUtil.copyProperties(one, userDTO, true);
            // session有效且用户存在，返回用户信息
            return Result.success(userDTO);
        } else {
            // session无效或者用户不存在，返回错误
            return Result.error(Constants.CODE_600,"Session已过期或不存在此用户");
        }
    }

    @PostMapping("/register")// 注册
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400,"参数错误");
        }//校验
        return Result.success(userService.register(userDTO));
    }

    @PostMapping// 新增或者更新
    public Result save(@RequestBody User user) {
        if(userService.saveOrUpdate(user)){
            return Result.success();
        }
        else{
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")//按照id删除
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @DeleteMapping("/del/batch")//按照id批量删除
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping//返回所有用户数据
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")//按id查找返回
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/username/{username}")//按用户名查找返回
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/page")//返回页面数据
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {//一定要注意这里的初始值，因为没传初始值不代表为空，所以会报错
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("user_name", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("user_email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("user_address", address);
        }
        queryWrapper.orderByDesc("user_id");
        return userService.page(page, queryWrapper);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }

        userService.saveBatch(users);
        return true;
    }
}

