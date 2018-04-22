package com.lxb.dao;

import com.lxb.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAllDept(); // add getAllDept method

    List<SysDept> getChildDeptListByLevel(@Param("level") String level); // add getChildDeptListByLevel method

    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList); // add batchUpdateLevel method

    int countByNameAndParentId(@Param("parentId") int parentId, @Param("name") String name, @Param("id") Integer id); // add countByNameAndParentId method
}