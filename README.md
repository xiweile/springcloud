# springcloud应用基础框架

功能清单
 - eureka注册中心
 - springcloud-config配置中心
 - zuul 网关
 - 基于oauth2 的单点登录
 - 基于springsecurity权限控制
 - bus-kafka配置消息通知机制
 - feign 接口调用


```sql
-- Table oauth客户端配置信息表
CREATE TABLE IF NOT EXISTS `demo`.`oauth_client_details` (
  `client_id` VARCHAR(128) NOT NULL,
  `resource_ids` VARCHAR(256) NULL DEFAULT NULL,
  `client_secret` VARCHAR(256) NULL DEFAULT NULL,
  `scope` VARCHAR(256) NULL DEFAULT NULL,
  `authorized_grant_types` VARCHAR(256) NULL DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256) NULL DEFAULT NULL,
  `authorities` VARCHAR(256) NULL DEFAULT NULL,
  `access_token_validity` INT(11) NULL DEFAULT NULL,
  `refresh_token_validity` INT(11) NULL DEFAULT NULL,
  `additional_information` VARCHAR(4096) NULL DEFAULT NULL,
  `autoapprove` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
```