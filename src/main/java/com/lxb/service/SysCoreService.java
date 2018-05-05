package com.lxb.service;

import com.google.common.collect.Lists;
import com.lxb.common.RequestHolder;
import com.lxb.dao.SysAclMapper;
import com.lxb.dao.SysRoleAclMapper;
import com.lxb.dao.SysRoleUserMapper;
import com.lxb.model.SysAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * Get permissions of the current user
     * @return a list of permission
     */
    public List<SysAcl> getCurrentUserAclList() {

        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    /**
     * Get permissions of the current role
     * @param roleId role id
     * @return a list of permission
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    public List<SysAcl> getUserAclList(int userId) {

        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }

        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }

        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }

        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        return true;
    }
}
