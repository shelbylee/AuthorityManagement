package com.lxb.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空噢")
    @Length(max = 15, min = 2, message = "部门名称长度需在2-15字之间")
    private String name;

    private Integer parentId;

    @NotNull(message = "展示顺序不可为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150字")
    private String remark;

}
