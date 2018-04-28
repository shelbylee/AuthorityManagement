package com.lxb.service;

import com.lxb.dao.SysUserMapper;
import com.lxb.exception.ParamException;
import com.lxb.model.SysUser;
import com.lxb.param.UserParam;
import com.lxb.util.BeanValidator;
import com.lxb.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public void save(UserParam param) {

        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("该电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("该邮箱已被占用");
        }

        String passwd = PasswordUtil.randomPasswd();
        // TODO:
        passwd = "12345678";

        SysUser user = SysUser.builder()
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .password(passwd)
                .deptId(param.getDeptId())
                .status(param.getStatus())
                .remark(param.getRemark()).build();

        user.setOperator("system"); // TODO:
        user.setOperateIp("127.0.0.1"); // TODO:
        user.setOperateTime(new Date());

        // TODO: sendEmail
        sysUserMapper.insertSelective(user);
    }

    public boolean checkEmailExist(String mail, Integer userId) {
        return false;
    }

    public boolean checkTelephoneExist(String mail, Integer userId) {
        return false;
    }
}
