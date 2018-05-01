package com.lxb.service;

import com.google.common.base.Preconditions;
import com.lxb.common.RequestHolder;
import com.lxb.dao.SysAclModuleMapper;
import com.lxb.exception.ParamException;
import com.lxb.model.SysAclModule;
import com.lxb.param.AclModuleParam;
import com.lxb.util.BeanValidator;
import com.lxb.util.IpUtil;
import com.lxb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    public void save(AclModuleParam param) {

        // param check
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule aclModule = SysAclModule.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();

        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(aclModule);
    }

    public void update(AclModuleParam param) {

        // param check
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule after = SysAclModule.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    /**
     * If current acl module is updated, it's child acl module also needs to be updated(it's level value).
     * And their update operations need to success or fail simultaneously.
     * @param before the acl module info before updating
     * @param after the acl module info after updating
     */
    @Transactional
    void updateWithChild(SysAclModule before, SysAclModule after) {

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        if (!after.getLevel().equals(before.getLevel())) {
            // if the level of the updated acl module has changed, we need to update it's child acl module
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclModuleList)) { // if child acl module is not null
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel(); // get current child acl module's level
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length()); // calculate it's new level
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }

        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * To make sure NO acl module with the SAME NAME can exist in the same level
     * @param parentId
     * @param aclModuleName
     * @param deptId
     * @return true or false
     */
    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);

        if (aclModule == null) {
            return null;
        }

        return aclModule.getLevel();
    }
}
