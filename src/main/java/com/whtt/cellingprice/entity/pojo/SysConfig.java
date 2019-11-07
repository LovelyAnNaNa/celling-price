package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author 
 * @since 2019-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfig extends Model<SysConfig> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置名称
     */
    private String configKey;

    /**
     * 配置信息,系统使用
     */
    private String configValue;

    /**
     * 配置备注,显示给前台用户查看
     */
    private String remarks;

    /**
     * 预留字段
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String CONFIG_KEY = "config_key";

    public static final String CONFIG_VALUE = "config_value";

    public static final String REMARKS = "remarks";

    public static final String STANDBY_APPLICATION = "standby_application";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
