package com.lxb.service;

import com.lxb.dao.SysDeptMapper;
import com.lxb.exception.ParamException;
import com.lxb.model.SysDept;
import com.lxb.param.DeptParam;
import com.lxb.util.BeanValidator;
import com.lxb.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.logging.Level;

@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam param) {

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

    /**
     * To make sure NO department with the SAME NAME can exist in the same hierarchy
     * @param parentId
     * @param deptName
     * @param deptId
     * @return true or false
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        // TODO:
        return true;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);

        if (dept == null) {
            return null;
        }

        return dept.getLevel();
    }
}
