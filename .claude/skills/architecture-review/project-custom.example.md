# 项目专属架构检查项

> 本文件为示例模板。将此 Skill 复制到项目级 `.cursor/skills/architecture-review/` 后，
> 复制本文件为 `project-custom.md` 并根据项目实际情况填写。
>
> Agent 会在评估时自动读取 `project-custom.md`（不会读取 `.example.md`）。

## 项目信息

- **项目名称**：[填写项目名称]
- **架构风格**：[微服务 / 单体 / 模块化单体 / 事件驱动 / ...]
- **核心技术栈**：[Spring Boot + MyBatis + Redis / Vue3 + Vite / ...]

## 项目专属检查项

> 每个检查项包含：名称、规范要求、检查方法。
> 可根据项目需要增减条目。

### 检查项 1：[示例 - 包结构规范]

**规范要求**：
```
项目必须遵循以下包结构：
com.company.project
├── controller/    # 接口层
├── service/       # 业务层
│   └── impl/
├── mapper/        # 数据访问层
├── model/
│   ├── entity/    # 数据库实体
│   ├── dto/       # 传输对象
│   └── vo/        # 展示对象
├── config/        # 配置类
└── common/        # 公共工具
```

**检查方法**：
- 检查源码目录是否符合上述包结构
- 是否存在不属于规范的包
- 实体类是否放在 entity 包下而非 mapper 包下

---

### 检查项 2：[示例 - 微服务通信规范]

**规范要求**：
- 服务间调用必须通过 OpenFeign，禁止使用 RestTemplate 直连
- 所有 Feign 接口必须配置降级（fallback）
- 服务间传参使用 DTO，禁止直接传递 Entity

**检查方法**：
- 搜索 RestTemplate 的使用，确认是否存在违规直连
- 检查 Feign 接口是否都配置了 fallbackFactory
- 检查 Feign 方法参数类型是否为 DTO

---

### 检查项 3：[示例 - 数据库规范]

**规范要求**：
- 所有表必须有 create_time、update_time、deleted 字段
- 禁止在代码中使用物理删除，必须使用逻辑删除
- 分页查询必须使用 PageHelper 或 MyBatis-Plus 分页插件

**检查方法**：
- 检查 Entity 类是否包含规定字段
- 搜索 DELETE FROM 语句，确认是否存在物理删除
- 检查分页查询的实现方式

---

### 检查项 N：[自行添加...]

**规范要求**：
[填写]

**检查方法**：
[填写]

---

## 项目专属禁止/推荐清单（可选）

> 如果项目有额外的组件限制（超出公司统一清单），可在此补充。

| 组件 | 状态 | 说明 | 替换为 |
|------|------|------|--------|
| [示例] xxx-old-sdk | 禁止 | 已废弃的内部 SDK | xxx-new-sdk |
