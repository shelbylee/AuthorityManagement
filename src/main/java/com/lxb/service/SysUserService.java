package com.lxb.service;

import com.google.common.base.Preconditions;
import com.lxb.dao.SysUserMapper;
import com.lxb.exception.ParamException;
import com.lxb.model.SysUser;
import com.lxb.param.UserParam;
import com.lxb.util.BeanValidator;
import com.lxb.util.MD5Util;
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

        String password = PasswordUtil.randomPasswd();
        // TODO:
        password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);

        SysUser user = SysUser.builder()
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .password(encryptedPassword)
                .deptId(param.getDeptId())
                .status(param.getStatus())
                .remark(param.getRemark()).build();

        user.setOperator("system"); // TODO:
        user.setOperateIp("127.0.0.1"); // TODO:
        user.setOperateTime(new Date());

        // TODO: sendEmail
        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("该电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("该邮箱已被占用");
        }

        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder()
                .id(param.getId())
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .deptId(param.getDeptId())
                .status(param.getStatus())
                .remark(param.getRemark()).build();

        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    public boolean checkEmailExist(String mail, Integer userId) {
        return false;
    }

    public boolean checkTelephoneExist(String mail, Integer userId) {
        return false;
    }
}
