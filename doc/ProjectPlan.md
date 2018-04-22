### 项目规划（暂定）

#### 一、基本目标：

- 基于扩展的RBAC实现
- 易于扩展，能灵活适应需求的变化
- 所有管理都有界面，方便操作

#### 二、需要实现的具体功能

- 部门列表（树型结构）
    - 新增、修改、删除部门
        - 新增部门：新增部门的上级部门（通过点击添加）、名称、顺序、备注。保存时需要校验同级下是否有相同名称的部门，不同级别下部门名称是可以相同的
        - 修改部门：
        - 删除部门：需要确认下面是否有子部门 or 人员

点击部门 -> 进入用户列表界面

- 用户列表（在部门列表下）
    - 分页展示用户信息
    - 分页信息的维护
    - 支持搜索式的分页支持 *
    - 新增、修改、删除、查看用户
        - 新增：所在部门（点击添加）、名称、邮箱、电话、状态、备注

- 权限模块列表（树型结构） 类似于部门列表
    - 新增权限：上级模块、名称、顺序、状态、备注
    - 新增权限点：所属权限模块、名称、类型（菜单、按钮、其他）、URL、状态、顺序、备注

- 角色列表
    - 新增、修改、删除
    - 超级管理员
    - 普通管理员

- 核心页面
    - 角色与权限
    - 角色与用户

#### 三、整体需要开发的内容：

- 配置管理类功能
    - 用户、权限、角色的管理界面
    - 角色-用户管理、角色-权限管理
    - 权限更新日志管理
- 权限拦截类功能
    - 在切面做权限拦截
    - 确定用户是否拥有某个权限
- 辅助类功能：缓存、各种树结构生成（继续学习）
    - Redis的封装和使用
    - 部门树、权限模块树、角色权限树、用户权限树
    - 权限操作恢复

*************

### Project Plan (temporary)

#### I. Basic goals:

- Extended RBAC implementation
- Easy to expand, flexible to adapt to changing requirements
- All management has an interface for easy operation

#### Ⅱ. Specific functions 

- Department List (Tree Structure)
    - Add, modify, delete departments
    - Add department: new department's parent department (click to add), name, sequence, and remarks. When saving, you need to check whether there is a department with the same name under the same level. The department name can be the same under different levels.
    - Modify department:
    - Delete department: need to confirm if there is any sub-department or personnel

Click Department -> enter user list interface

- User list (under the department list)
    - Pagination of user information
    - Maintenance of paging information
    - Support search page support *
    - Add, modify, delete, view user
        - Add: Department (click to add), Name, Email, Phone, Status, Remarks

- Permissions Module List (Tree Structure) Similar to Department List
    - Add permission: superior module, name, sequence, status, remarks
    - Added authority points: affiliation module, name, type (menu, button, other), URL, status, sequence, comment

- Role list
    - Add, modify, delete
    - Super administrator
    - Ordinary administrator

- Core page
    - Roles and permissions
    - Roles and users

#### Ⅲ.  the overall needs of the development of content:

- Configure Management Functions
    - User, permission, role management interface
    - Role - User Management, Role - Permissions Management
    - Permissions update log management
- Privilege interception function
    - Permission interception on Fliter
    - Determine if the user has a permission
- Auxiliary functions: cache, various tree structure generation (still learning)
    - Package and use of Redis
    - Department tree, permission module tree, role permission tree, user permission tree
    - Permission operation recovery