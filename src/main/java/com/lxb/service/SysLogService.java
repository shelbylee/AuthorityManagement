package com.lxb.service;

import com.lxb.bean.LogType;
import com.lxb.common.RequestHolder;
import com.lxb.dao.SysLogMapper;
import com.lxb.model.*;
import com.lxb.util.IpUtil;
import com.lxb.util.JsonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    public void saveDeptLog(SysDept before, SysDept after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveUserLog(SysUser before, SysUser after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveAclLog(SysAcl before, SysAcl after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();

        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }
}
