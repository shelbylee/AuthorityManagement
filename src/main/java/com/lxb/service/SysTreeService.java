package com.lxb.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.lxb.dao.SysDeptMapper;
import com.lxb.dto.DeptLevelDto;
import com.lxb.model.SysDept;
import com.lxb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }

        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }

        Multimap<String, DeptLevelDto> levelDtoMultimap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : dtoList) {
            levelDtoMultimap.put(dto.getLevel(), dto);

            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        // asc sort by Seq
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        // build a tree recursively
        transformDeptTree(rootList, LevelUtil.ROOT, levelDtoMultimap);

        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelDtoList,
                                  String level,
                                  Multimap<String, DeptLevelDto> levelDtoMultimap) {

        for (int i = 0; i < deptLevelDtoList.size(); i++) {
            // traverse
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            // calculate next level from current level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // get next level
            List<DeptLevelDto> tmpDeptList = (List<DeptLevelDto>)levelDtoMultimap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(tmpDeptList)) {
                // sort by Seq
                Collections.sort(tmpDeptList, deptSeqComparator);
                // set next dept
                deptLevelDto.setDeptLevelDtoList(tmpDeptList);
                // recursive
                transformDeptTree(tmpDeptList, nextLevel, levelDtoMultimap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
