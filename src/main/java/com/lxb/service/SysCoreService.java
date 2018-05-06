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
import java.util.Set;
import java.util.stream.Collectors;

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
     * @return a list of permissions
     */
    public List<SysAcl> getCurrentUserAclList() {

        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    /**
     * Get permissions of the current role
     * @param roleId role id
     * @return a list of permissions
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * Get permissions according to user id
     * @param userId user id
     * @return a list of permissions
     */
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
        /*
        We can define who is super admin here.
        For example, we can choose a user to be super admin or get a user from profile
         */
        // TODO:
        return true;
    }

    /**
     * Define whether current user has permission to access this url according to our rule.
     *
     * Our rule is:
     * If current user has one of the current url's access control list,
     * we think this user has permission to access current url.
     * @param url url
     * @return true or false
     */
    public boolean hasUrlAcl(String url) {

        // super admin
        if (isSuperAdmin()) {
            return true;
        }

        // current url's access control list
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }

        List<SysAcl> userAclList = getCurrentUserAclList();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        // if this url has valid acl
        boolean hasValidAcl = false;

        // RULE:
        for (SysAcl acl : aclList) {
            // invalid acl
            if (acl == null || acl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }

/*        if (!hasValidAcl) {
            return true;
        }
        return false;*/
        return !hasValidAcl;
    }
}
