```text
# .claude/skills/pr/SKILL.md
---
name: pr
description: 创建格式化的 PR
disable-model-invocation: true
---
为当前分支创建 Pull Request。

1. `git log main..HEAD --oneline` 获取提交历史
2. `git diff main --stat` 获取变更统计
3. 编写 PR 标题和描述
4. 使用 `gh pr create` 创建
5. 输出 PR URL
```