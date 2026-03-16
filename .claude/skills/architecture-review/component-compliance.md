# 公司组件合规性要求

本文档列出公司内部禁止使用或禁止新增的组件/中间件清单。
架构评估时需检查项目依赖是否存在违规引用。

## 状态定义

| 状态 | 含义 | 要求 |
|------|------|------|
| **禁止** | 已被禁止使用在**任何**项目/产品中 | 必须移除并替换为推荐组件 |
| **退出** | 不在允许使用的范围 | 现有项目应尽快移除 |
| **禁止新增** | 存量项目可暂时继续使用 | **不允许在新项目/产品中使用** |
| **允许例外** | 仅被允许在特定项目/产品中使用 | 需单独审批说明 |

## 检查方式

读取项目的依赖声明文件（pom.xml / build.gradle / package.json），与下方清单逐一比对。

## Java 禁止使用的组件

| GroupId | ArtifactId | 状态 | 建议 | 替换为 |
|---------|-----------|------|------|--------|
| javax.activation | activation | 禁止 | 替换 | `jakarta.activation:jakarta.activation-api` |
| com.ctrip.framework.apollo | apollo-adminservice | 禁止 | 禁用 (老旧包，需要替换。) |  |
| com.ctrip.framework.apollo | apollo-configservice | 禁止 | 禁用 (老旧包，需要替换。) |  |
| com.ctrip.framework.apollo | apollo-portal | 禁止 | 禁用 (老旧包，需要替换。) |  |
| com.googlecode.aviator | aviator | 禁止新增 |  |  |
| axis | axis | 禁止 | 替换 | `org.apache.axis2:axis2` |
| org.apache.commons.codec | commons-codec | 禁止 | 替换 | `commons-codec:commons-codec` |
| org.apache | commons-httpclient | 禁止 | 替换 | `org.apache.httpcomponents:httpclient` |
| org.apache.commons.httpclient | commons-httpclient | 禁止 | 替换 | `org.apache.httpcomponents:httpclient` |
| org.apache.commons.lang | commons-lang | 禁止 | 替换 | `org.apache.commons:commons-lang3` |
| commons-logging | commons-logging-api | 禁止 | 替换 | `commons-logging:commons-logging` |
| org.apache.commons | commons-net | 禁止 | 替换 | `commons-net:commons-net` |
| concurrent | concurrent | 禁止 | 禁用 (推荐 jdk自带的ThreadPoolExecutor) |  |
| ezmorph | ezmorph | 禁止 | 替换 | `net.sf.ezmorph:ezmorph` |
| org.csource | fastdfs-client | 禁止 | 替换 | `org.csource:fastdfs-client-java` |
| org.codehaus.groovy | groovy | 禁止 | 替换 | `org.codehaus.groovy:groovy-all` |
| org.fusesource.hawtbuf | hawtbuf | 禁止 | 替换 | `com.google.protobuf:protobuf-java` |
| math.geom2d | javaGeom | 禁止 | 替换 | `org.locationtech.jts:jts-core` |
| com.google.code.javaparser | javaparser | 禁止新增 |  |  |
| net.sourceforge.jchardet | jchardet | 禁止 | 替换 | `com.ibm.icu:icu4j` |
| org.jfree | jcommon | 禁止 | 替换 | `org.apache.commons:commons-lang3` |
| org.jeecgframework.boot | jeecg-module-sequence | 禁止 | 禁用 (老旧包，需要替换。) |  |
| org.jeecgframework.boot | jeecg-system-biz | 禁止 | 禁用 (老旧包，需要替换。) |  |
| com.jcraft | jsch | 禁止 | 替换 | `com.github.mwiede:jsch` |
| jsch | jsch | 禁止 | 替换 | `com.github.mwiede:jsch` |
| json | json-lib | 禁止 | 替换 | `com.fasterxml.jackson.core:jackson-core` |
| de.odysseus.juel | juel-impl | 禁止 | 替换 | `jakarta.el:jakarta.el-api` |
| net.sf.luaj | luaj-jse | 禁止 | 替换 | `org.luaj:luaj-jse` |
| org.keplerproject | luajava | 禁止 | 替换 | `org.luaj:luaj-jse` |
| com.rxjava | rxjava | 禁止 | 替换 | `io.reactivex.rxjava3:rxjava` |
| org.mitre.dsmiley.httpproxy | smiley-http-proxy-servlet | 禁止 | 禁用 (避免安全问题，代理服务器请使用独立的应用实现) |  |
| soap | soap | 禁止 | 替换 | `jakarta.xml.soap:jakarta.xml.soap-api` |
| org.springframework.boot | spring-boot-admin-starter-client | 禁止 | 替换 | `de.codecentric:spring-boot-admin-starter-client` |
| org.springframework.boot | spring-boot-admin-starter-server | 禁止 | 替换 | `de.codecentric:spring-boot-admin-starter-server` |
| org.apache | ws.commons.schema | 禁止 | 替换 | `org.apache.ws.xmlschema:xmlschema-core` |
| com.github.sgroschupf | zkclient | 禁止新增 | 替换 | `com.101tec:zkclient` |
| com.github.xiaoymin | knife4j-spring-boot-starter | 禁止 | 替换 | `org.springdoc:springdoc-openapi-starter-webmvc-ui` |
| ma.glasnost.orika | orika-core | 禁止 | 替换 | `org.mapstruct:mapstruct` |
| ru.yandex.clickhouse | clickhouse-jdbc | 禁止 | 替换 | `com.clickhouse:clickhouse-jdbc` |
| net.sourceforge.jexcelapi | jxl | 禁止 | 替换 | `com.alibaba:easyexcel` |
| org.jetbrains | annotations | 禁止 | 替换 | `javax.annotation:javax.annotation-api` |
| com.fifesoft | rsyntaxtextarea | 禁止 | 禁用 (Java Swing应用，一般不会用，有特殊情况单独说明申请。) |  |
| com.sun.mail | javax.mail | 禁止 | 替换 | `org.apache.commons:commons-email` |
| com.alibaba | fastjson | 禁止 | 替换 | `com.alibaba.fastjson2:fastjson2` |
| net.sf.json-lib | json-lib | 禁止 | 替换 | `com.fasterxml.jackson.core:jackson-core` |
| net.minidev | json-smart | 禁止 | 替换 | `com.fasterxml.jackson.core:jackson-core` |
| mysql | mysql-connector-java | 禁止 | 替换 | `com.mysql:mysql-connector-j` |
| org.springframework.cloud | spring-cloud-starter-alibaba-nacos-config | 禁止 | 替换 | `com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config` |
| org.springframework.cloud | spring-cloud-starter-alibaba-nacos-discovery | 禁止 | 替换 | `com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery` |
| oceanbase-client | oceanbase-client | 禁止 | 替换 | `com.oceanbase:oceanbase-client` |
| ojdbc | ojdbc | 禁止 | 替换 | `com.oracle.database.jdbc:ojdbc8` |
| com.oracle.ojdbc | ojdbc8 | 禁止 | 替换 | `com.oracle.database.jdbc:ojdbc8` |
| cn.easyproject | orai18n | 禁止 | 替换 | `com.oracle.database.nls:orai18n` |
| com.oracle.ojdbc | orai18n | 禁止 | 替换 | `com.oracle.database.nls:orai18n` |
| org.jvnet.hudson | ganymed-ssh2 | 禁止 | 替换 | `ch.ethz.ganymed:ganymed-ssh2` |
| org.tmatesoft.svnkit | svnkit | 禁止 | 禁用 (生产环境不需要使用代码库工具，有特殊情况单独说明申请。) |  |
| org.apache.axis | axis | 禁止 | 替换 | `org.apache.axis2:axis2` |
| axis | axis-jaxrpc | 禁止 | 替换 | `org.apache.axis2:axis2` |
| org.apache.axis | axis-jaxrpc | 禁止 | 替换 | `org.apache.axis2:axis2` |
| org.apache.axis | axis-saaj | 禁止 | 替换 | `org.apache.axis2:axis2` |
| axis | axis-wsdl4j | 禁止 | 替换 | `org.apache.axis2:axis2` |
| dom4j | dom4j | 禁止 | 替换 | `org.dom4j:dom4j` |
| org.apache.directory.studio | org.dom4j.dom4j | 禁止 | 替换 | `org.dom4j:dom4j` |
| org.eclipse.jgit | org.eclipse.jgit | 禁止 | 禁用 (生产环境不需要使用代码库工具，有特殊情况单独说明申请。) |  |
| com.h2database | h2 | 禁止 | 禁用 (生产环境不需要使用h2，有特殊情况单独说明申请。) |  |
| com.github.luben | zstd-jni | 禁止 | 替换 | `net.jpountz.lz4:lz4` |
| commons-lang | commons-lang | 禁止 | 替换 | `org.apache.commons:commons-lang3` |
| commons-pool | commons-pool | 禁止 | 替换 | `org.apache.commons:commons-pool2` |
| org.apache.ant | ant | 禁止 | 禁用 (生产环境不需要使用编译工具，有特殊情况单独说明申请。) |  |
| com.dcfs | ftp | 禁止 | 替换 | `com.github.mwiede:jsch` |
| log4j | log4j | 禁止 | 替换 | `org.apache.logging.log4j:log4j-core` |
| ch.qos.reload4j | reload4j | 禁止 | 替换 | `org.apache.logging.log4j:log4j-core` |
| net.dongliu | apk-parser | 禁止 | 禁用 (生产环境不需要使用此工具，有特殊情况单独说明申请。) |  |
| com.github.penggle | kaptcha | 禁止 | 替换 | `org.dromara.hutool:hutool-all` |
| pro.fessional | kaptcha | 禁止 | 替换 | `com.github.penggle:kaptcha` |
| com.dangdang | elastic-job-common-core | 禁止 |  |  |
| com.dangdang | elastic-job-core | 禁止 |  |  |
| com.dangdang | elastic-job-lite-core | 禁止 |  |  |
| com.dangdang | elastic-job-lite-lifecycle | 禁止 | 替换 | `com.xuxueli:xxl-job-core` |
| com.dangdang | elastic-job-lite-spring | 禁止 |  |  |
| com.dangdang | elastic-job-spring | 禁止 |  |  |
| org.hibernate | hibernate-core | 禁止新增 | 替换 | `org.mybatis:mybatis` |
| org.hibernate.orm | hibernate-core | 禁止新增 |  |  |
| org.hibernate | hibernate-entitymanager | 禁止新增 | 替换 | `org.mybatis:mybatis` |
| org.hibernate.javax.persistence | hibernate-jpa-2.1-api | 禁止新增 | 替换 | `org.mybatis:mybatis` |
| org.hibernate | hibernate-re | 禁止新增 |  |  |
| org.hibernate.validator | hibernate-validator | 禁止新增 | 替换 | `org.mybatis:mybatis` |
| org.hibernate | hibernate-validator | 禁止新增 |  |  |
| com.lowagie | itext | 禁止 | 替换 | `com.itextpdf:itext-core` |
| org.mapstruct | mapstruct-jdk8 | 禁止 | 替换 | `org.mapstruct:mapstruct` |
| org.jboss.netty | netty | 禁止 | 替换 | `io.netty:netty-all` |
| org.activiti | activiti-bpmn-converter | 禁止新增 |  |  |
| org.activiti | activiti-bpmn-model | 禁止新增 |  |  |
| org.activiti | activiti-diagram-rest | 禁止新增 |  |  |
| org.activiti | activiti-engine | 禁止新增 |  |  |
| org.activiti | activiti-explorer | 禁止新增 |  |  |
| org.activiti | activiti-image-generator | 禁止新增 |  |  |
| org.activiti | activiti-json-converter | 禁止新增 |  |  |
| com.activiti | activiti-model | 禁止新增 |  |  |
| org.activiti | activiti-modeler | 禁止新增 |  |  |
| org.activiti | activiti-process-validation | 禁止新增 |  |  |
| org.activiti | activiti-rest | 禁止新增 |  |  |
| org.activiti | activiti-spring | 禁止新增 |  |  |
| org.activiti | activiti-spring-boot-starter | 禁止新增 |  |  |
| org.activiti | activiti-spring-boot-starter-actuator | 禁止新增 |  |  |
| org.activiti | activiti-spring-boot-starter-basic | 禁止新增 | 替换 | `org.flowable:flowable-spring-boot-starter` |
| org.activiti | activiti-spring-boot-starter-jpa | 禁止新增 |  |  |
| org.activiti | activiti-spring-boot-starter-rest-api | 禁止新增 |  |  |
| com.lowagie | itextasian | 禁止 | 替换 | `com.itextpdf:itext-core` |
| com.lowagie | itextasiancmaps | 禁止 | 替换 | `com.itextpdf:itext-core` |

## 禁止使用的中间件

| 名称 | 状态 | 说明 |
|------|------|------|
| Ambari Metrics | 禁止新增 |  |
| ansible | 禁止新增 |  |
| canal | 禁止新增 |  |
| chukwa | 禁止新增 |  |
| cockroach | 禁止新增 |  |
| consul | 禁止新增 |  |
| datax | 禁止新增 |  |
| derby | 禁止新增 |  |
| Docker | 禁止新增 |  |
| dubbo | 禁止新增 |  |
| Eureka | 禁止新增 |  |
| Glusterfs | 禁止新增 |  |
| haproxy | 禁止新增 |  |
| jstorm | 禁止新增 |  |
| kettle | 禁止新增 |  |
| Kibana | 禁止新增 |  |
| kubernetes | 禁止新增 |  |
| logstash | 禁止新增 |  |
| openGauss | 禁止新增 |  |
| openoffice | 禁止新增 |  |
| opensips | 禁止新增 |  |
| phoenix | 禁止新增 |  |
| Pig | 禁止新增 |  |
| pika | 禁止 | 建议使用redis进行替换 |
| Ranger | 禁止新增 |  |
| Solr | 禁止新增 |  |
| Storm | 禁止新增 |  |
| supervisor | 禁止新增 |  |
| Tez | 禁止新增 | A generalized data-flow programming framework, built on Hadoop YARN, which provi |
| tidb | 禁止新增 |  |
| ttserver | 禁止新增 |  |
| varnish | 禁止新增 |  |
| YARN | 禁止新增 |  |
| Jenkins | 禁止新增 | Jenkins是一个开源软件项目，是基于Java开发的一种持续集成工具，用于监控持续重复的工作，旨在提供一个开放易用的软件平台，使软件项目可以进行持续集成。 |
| OpenLdap | 禁止新增 | 轻型目录访问协议 |
| Yapi | 禁止新增 | YApi 是一个可本地部署的、打通前后端及QA的、可视化的接口管理平台 |
| TDengine | 禁止新增 | TDengine是涛思数据开发的一款高性能、分布式的物联网、工业大数据平台，其核心模块是高性能、集群开源、云原生、极简的时序数据库。TDengine专为物联网、 |
| GBase | 禁止新增 | GBase 是南大通用数据技术有限公司推出的自主品牌的数据库产品 |
| Greenplum | 禁止新增 | Greenplum的架构采用了MPP(大规模并行处理)。在 MPP 系统中，每个 SMP节点也可以运行自己的操作系统、数据库等。换言之，每个节点内的 CPU 不 |
| mariadb | 禁止新增 |  |
| mysql-Percona | 禁止新增 |  |
| Oracle | 禁止新增 | Oracle开发的关系数据库产品因性能卓越而闻名，Oracle数据库产品为财富排行榜上的前1000家公司所采用，许多大型网站也选用了Oracle系统，是世界最好 |
| PanWeiDB | 禁止新增 | 磐维数据库（PanWeiDB）是中国移动首个基于中国本土开源数据库openGauss打造的面向ICT基础设施的自研数据库产品。 |
| Vertica | 禁止新增 | Vertica是一款基于列存储的MPP（massively parallel processing）架构的数据库。 |
| Ceph | 禁止新增 | Ceph是一种为优秀的性能、可靠性和可扩展性而设计的统一的、分布式文件系统。ceph 的统一体现在可以提供文件系统、块存储和对象存储，分布式体现在可以动态扩展。 |
| minio | 禁止新增 |  |
| codis | 禁止 | 建议使用redis进行替换 |
| memcached | 禁止新增 |  |

## Vue 禁止使用的组件

| 名称 | 状态 | 类别 | 说明 |
|------|------|------|------|
| concurrently | 禁止 | 工具 | 禁止在任何项目中使用 |