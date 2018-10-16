package com.weiller.gateway.comm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 忽略过滤路径配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "filter.path")
public class FilterPathConfigProperties {

    private String ignores;

    private String login;

    private String logout;

}
