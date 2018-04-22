package com.lxb.service;

import com.google.common.base.Preconditions;
import com.lxb.dao.SysDeptMapper;
import com.lxb.exception.ParamException;
import com.lxb.model.SysDept;
import com.lxb.param.DeptParam;
import com.lxb.util.BeanValidator;
import com.lxb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam param) {

        // param check
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept dept = SysDept.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        dept.setOperator("system"); // TODO:
        dept.setOperateIp("127.0.0.1"); // TODO:
        dept.setOperateTime(new Date());

        sysDeptMapper.insertSelective(dept);
    }

    public void update(DeptParam param) {

        // param check
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId()); // the dept need to be updated
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept after = SysDept.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator("system-update"); // TODO:
        after.setOperateIp("127.0.0.1"); // TODO:
        after.setOperateTime(new Date());

        updateWithChild(before, after);

    }

    /**
     * If current dept is updated, it's child dept also needs to be updated(it's level value).
     * And their update operations need to success or fail simultaneously.
     * @param before the dept info before updating
     * @param after the dept info after updating
     */
    @Transactional
    public void updateWithChild(SysDept before, SysDept after) {

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        if (!after.getLevel().equals(before.getLevel())) {
            // if the level of the updated dept has changed, we need to update it's child dept
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)) { // if child dept is not null
                for (SysDept dept : deptList) {
                    String level = dept.getLevel(); // get current child dept's level
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length()); // calculate it's new level
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }

        sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * To make sure NO dept with the SAME NAME can exist in the same level
     * @param parentId
     * @param deptName
     * @param deptId
     * @return true or false
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);

        if (dept == null) {
            return null;
        }

        return dept.getLevel();
    }
}
