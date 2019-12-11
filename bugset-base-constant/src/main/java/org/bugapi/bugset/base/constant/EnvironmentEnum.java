package org.bugapi.bugset.base.constant;

/**
 * 系统环境类型
 *
 * @author zhangxw
 * @since 0.0.1
 */
public enum EnvironmentEnum {

    /**
     * 开发环境
     */
    DEV("dev"),
    /**
     * 测试环境
     */
    TEST("test"),
    /**
     * 线上预发环境
     */
    PRE("pre"),
    /**
     * 生产环境
     */
    PRO("pro");

    /**
     * 类型
     */
    private String type;

    EnvironmentEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
