package com.lxb.service;

import com.google.common.collect.Lists;
import com.lxb.dao.SysRoleUserMapper;
import com.lxb.dao.SysUserMapper;
import com.lxb.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    public List<SysUser> getListByRoleId(int roleId) {

        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        return sysUserMapper.getByIdList(userIdList);
    }


}
