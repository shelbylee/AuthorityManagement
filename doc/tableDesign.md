#### 1. 部门表 (SysDept - sys_dept)
- id
- name：同级别不能重复
- level：在部门整个的层级，方便层级查询
- seq：该部门在当前层级的排序
- remark
- parentId：用来关联 id，方便找到上一层
- operator
- operateTime
- operateip

#### 2. 用户表 (SysUser - sys_user)
- id
- username
- telephone
- mail
- password
- remark
- deptid
- status：0: 冻结 1: 正常 2: 删除
- operator
- operateTime
- operateip

#### 3. 权限模块表 (SysAclModule - sys_acl_module)
- id
- name
- parentid
- level
- status
- seq
- remark
- operator
- operateTime
- operateip

#### 4. 权限表 (SysAcl - sys_acl)
- id
- code
- name
- aclModuleId：当前权限所在权限模块
- url：请求的url，可为空，可填正则表达式
- type：1: 菜单 2: 按钮 3: 其他
- status：0: 冻结 1: 正常
- seq
- remark
- operator
- operateTime
- operateIp

#### 5. 角色表 (SysRole - sys_role)
- id
- name
- type 1: 管理员 2: 其他
- status 1: 可用 0: 冻结
- remark
- operator
- operateTime
- operateIp

#### 6. 角色-用户表 (SysRoleUser - sys_role_user)
- id
- roleId
- aclId
- operator
- operateTime
- operateIp

#### 7. 权限记录表 (SysLog - sys_log)
- id
- type 1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系 (可以用枚举实现)
- targetId 基于type后指定的对象id，比如用户、权限、角色等表的主键
- oldValue
- newValue
- operator
- operateTime
- operateIp
- status 当前是否复原过，0：没有，1：复原过