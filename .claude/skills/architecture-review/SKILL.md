---
name: architecture-review
description: Evaluate code architecture for modularity, coupling, layering, extensibility, component compliance, and design pattern usage. Use when the user asks for architecture review, code structure evaluation, refactoring advice, dependency analysis, component compliance check, or mentions terms like "架构评估", "架构审查", "代码结构", "模块设计", "依赖分析", "重构建议", "分层设计", "组件合规", "禁止组件", "依赖检查".
---

# 代码架构合理性评估
## 概述

本 Skill 提供一套系统化的代码架构评估框架，分为 **公司级规范合规**、**通用架构评估** 和 **项目专属检查**（可选）三部分，输出结构化的中文评估报告文件。
框架版本：1.0.0

评估优先级：**公司规范合规 > 项目专属检查 > 通用架构质量**。合规性问题是硬性红线，必须优先处理。

## 评估工作流

```
任务进度：
- [ ] 第 1 步：确定评估范围
- [ ] 第 2 步：代码结构探索
- [ ] 第 3 步：公司级规范合规评估（优先）
- [ ] 第 4 步：项目专属检查（如有 project-custom.md）
- [ ] 第 5 步：通用架构评估（12 维度）
- [ ] 第 6 步：生成评估报告文件
```

### 第 1 步：确定评估范围

与用户确认：
- **评估目标**：全局架构 / 某个模块 / 某次变更
- **项目类型**：新项目 / 存量项目（影响合规性评判标准）
- **上下文**：项目阶段（初创 / 迭代 / 重构）、团队规模、业务特征

如果用户未指定，默认对整个项目进行全量评估。

### 第 2 步：代码结构探索

1. 通过目录结构了解项目整体布局
2. 识别入口文件、配置文件、依赖管理文件
3. 梳理核心模块及其边界
4. 绘制高层依赖关系图（用文字或 Mermaid）

### 第 3 步：公司级规范合规评估（优先）

> **此步骤优先级最高，必须最先执行。**合规问题是硬性红线，不受其他维度评分影响。

> **重要**：本 Skill 自带完整的公司禁止组件清单（JSON 数据文件），你**必须**读取这些数据来做判断，**不要**凭常识猜测。

**步骤 A — 读取禁止清单**：使用 Read 工具读取以下文件（路径相对于本 SKILL.md 所在目录）：
- `data/java-banned.json` — Java 禁止组件清单（110 条，含 groupId、artifactId、状态、替换建议）
- `data/middleware-banned.json` — 中间件禁止清单（48 条）
- `data/vue-banned.json` — Vue/前端禁止组件清单

**步骤 B — 读取项目依赖声明**：
- Java 项目：读取项目中所有 `pom.xml` 或 `build.gradle` 文件
- 前端项目：读取项目中的 `package.json`（排除 node_modules）
- 中间件：读取 `docker-compose*.yml`、`application*.yml` 等配置文件

**步骤 C — 逐一比对**：将项目依赖与禁止清单交叉比对

**步骤 D — 分级输出违规列表**：
- 🔴 禁止/退出：必须立即移除
- 🟡 禁止新增：新项目不得使用，存量项目排期替换

**状态定义**：

| 状态 | 含义 | 要求 |
|------|------|------|
| **禁止** | 已被禁止使用在**任何**项目/产品中 | 必须移除并替换 |
| **退出** | 不在允许使用范围 | 现有项目应尽快移除 |
| **禁止新增** | 存量项目可暂时继续使用，但**不允许在新项目/产品中使用** | 新项目禁用，老项目排期替换 |
| **允许例外** | 仅被允许在特定项目/产品中使用 | 需单独审批 |

**高频禁止组件速查**：

| 禁止组件 | 替换为 |
|---------|--------|
| `com.alibaba:fastjson` | `com.alibaba.fastjson2:fastjson2` |
| `commons-lang:commons-lang` | `org.apache.commons:commons-lang3` |
| `log4j:log4j` | `org.apache.logging.log4j:log4j-core` |
| `mysql:mysql-connector-java` | `com.mysql:mysql-connector-j` |
| `net.sf.json-lib:json-lib` | `com.fasterxml.jackson.core:jackson-core` |
| `com.jcraft:jsch` | `com.github.mwiede:jsch` |
| `dom4j:dom4j` | `org.dom4j:dom4j` |
| `com.github.xiaoymin:knife4j-spring-boot-starter` | `org.springdoc:springdoc-openapi-starter-webmvc-ui` |
| `ma.glasnost.orika:orika-core` | `org.mapstruct:mapstruct` |
| `commons-pool:commons-pool` | `org.apache.commons:commons-pool2` |
| `ch.qos.reload4j:reload4j` | `org.apache.logging.log4j:log4j-core` |
| `net.minidev:json-smart` | `com.fasterxml.jackson.core:jackson-core` |
| `org.hibernate:hibernate-core`（禁止新增） | `org.mybatis:mybatis` |
| activiti 系列（禁止新增） | `org.flowable:flowable-spring-boot-starter` |
| concurrently（Vue/前端，禁止） | 不得使用 |

**禁止中间件速查**：codis/pika（禁止）→ Redis，memcached/dubbo/eureka/tidb/minio（禁止新增）

完整清单在 `data/*.json` 中（步骤 A 已要求读取）。

**合规评分标准**（区分新项目和存量项目）：

| 等级 | 新项目 | 存量项目 |
|------|--------|---------|
| **A** | 无禁止、无退出、无禁止新增 | 无禁止/退出，禁止新增已有替换计划 |
| **B** | 无禁止/退出，1-2 个禁止新增有理由 | 无禁止，少量退出或禁止新增 |
| **C** | 存在禁止新增，或少量禁止有替换方案 | 存在禁止但有替换排期 |
| **D** | 使用了禁止/退出组件 | 大量禁止/退出且无整改计划 |

### 第 4 步：项目专属检查（可选）

> 本步骤仅在 Skill 目录下存在 `project-custom.md` 文件时执行。

使用 Read 工具尝试读取本文件同目录下的 `project-custom.md`：
- **如果文件存在**：按照其中定义的项目专属检查项逐一评估，结果纳入报告的"项目专属评估"章节
- **如果文件不存在**：跳过此步骤，继续执行第 5 步

`project-custom.md` 用于定义当前项目特有的架构规范和检查项（如微服务通信规范、包结构命名约定、数据库分片规则等）。可创建该文件进行定制。

文件格式参考见本文件同目录下的 [project-custom.example.md](project-custom.example.md)。

### 第 5 步：通用架构评估（12 维度）

以下 12 个维度逐一评估，每个维度给出评分（A/B/C/D）和具体分析：

| # | 维度 | 核心关注点 |
|---|------|-----------|
| 1 | 模块化与职责划分 | 单一职责、关注点分离、模块边界 |
| 2 | 依赖关系与耦合度 | 依赖方向、循环依赖、依赖倒置 |
| 3 | 分层架构合理性 | 层次定义、跨层调用、DTO 转换 |
| 4 | 扩展性与开放封闭 | OCP、策略模式、配置外化、YAGNI |
| 5 | 可测试性 | 依赖注入、接口抽象、测试金字塔 |
| 6 | 复杂度管理 | 圈复杂度、嵌套深度、函数/类大小 |
| 7 | 错误处理与弹性 | 异常策略、超时重试、降级方案 |
| 8 | API 与接口设计 | 接口隔离、契约稳定、版本管理 |
| 9 | 数据流与状态管理 | 数据流向、全局状态、副作用隔离 |
| 10 | 安全架构 | 认证鉴权边界、输入校验、最小权限 |
| 11 | 可观测性 | 结构化日志、链路追踪、健康检查 |
| 12 | 性能架构 | 缓存策略、N+1 问题、资源管理 |

各维度详细检查项见 [checklist.md](checklist.md)。

### 第 6 步：生成评估报告文件

评估完成后，**必须**执行以下操作：

1. 使用 Read 工具读取本文件同目录下的 `report-template.md` 获取报告模板
2. 按照模板格式填充评估内容（先总后分：总览 → 高危问题 → 合规详情 → 通用架构详情）
3. 使用 Write 工具将报告**写入项目根目录**，文件名为 `architecture-review-report.md`
4. 告知用户报告文件已生成及其路径

> 不要只在对话中输出报告内容，**必须同时生成一份 .md 文件**保存到项目中。

## 评分标准

| 等级 | 含义 | 标准 |
|------|------|------|
| **A** | 优秀 | 设计合理，符合最佳实践，几乎无改进空间 |
| **B** | 良好 | 整体合理，存在小幅优化空间 |
| **C** | 一般 | 存在明显问题，需要有针对性地改进 |
| **D** | 较差 | 存在严重问题，需要重点重构 |

## 评估原则

1. **合规红线**：公司规范合规是硬性要求，不因其他维度优秀而豁免
2. **务实优先**：不追求学术完美，结合项目阶段和团队现状给出可落地的建议
3. **证据驱动**：每个结论都要引用具体代码作为依据
4. **增量改进**：优先建议低成本高收益的改进，避免推翻重来

## 详细参考

- 完整检查清单：[checklist.md](checklist.md)
- 报告模板：[report-template.md](report-template.md)
- 组件合规清单：[component-compliance.md](component-compliance.md)
- 禁止组件数据：[data/java-banned.json](data/java-banned.json)、[data/middleware-banned.json](data/middleware-banned.json)、[data/vue-banned.json](data/vue-banned.json)
- 项目专属检查：[project-custom.md](project-custom.md)（可选，不存在则跳过）
- 项目专属模板：[project-custom.example.md](project-custom.example.md)
