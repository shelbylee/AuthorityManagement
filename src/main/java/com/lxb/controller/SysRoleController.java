package com.lxb.controller;

import com.lxb.common.JsonData;
import com.lxb.param.RoleParam;
import com.lxb.service.SysRoleAclService;
import com.lxb.service.SysRoleService;
import com.lxb.service.SysRoleUserService;
import com.lxb.service.SysTreeService;
import com.lxb.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleAclService sysRoleAclService;

    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

}
