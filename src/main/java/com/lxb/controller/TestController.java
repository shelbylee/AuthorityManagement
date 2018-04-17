package com.lxb.controller;

import com.lxb.common.ApplicationContextHelper;
import com.lxb.common.JsonData;
import com.lxb.dao.SysAclModuleMapper;
import com.lxb.exception.PermissionException;
import com.lxb.model.SysAclModule;
import com.lxb.param.TestVo;
import com.lxb.util.BeanValidator;
import com.lxb.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        throw new PermissionException("permission exception");
        //return JsonData.success("hello, my sys");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));

/*        try {
            Map<String, String> map = BeanValidator.validateObject(vo);
            if (MapUtils.isNotEmpty(map)) {
                for (Map.Entry<String, String> entry: map.entrySet()) {
                    log.info("{}->{}", entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {

        }*/
        return JsonData.success("test validate");
    }
}
