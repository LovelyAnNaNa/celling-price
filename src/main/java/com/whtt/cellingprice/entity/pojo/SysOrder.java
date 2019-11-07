package com.whtt.cellingprice.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbh
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_order")
public class SysOrder extends Model<SysOrder> {

    private static final long serialVersionUID=1L;

    /**
     * 顶价记录表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 要顶价的账号id
     */
    private Integer customerId;

    /**
     * 顶价的商品
     */
    private String commodity;

    /**
     * 订单状态,0下单,1顶价成功
     */
    private Integer status;

    /**
     * 顶价扣除的积分
     */
    private Integer deductIntegral;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;


    public static final String ID = "id";

    public static final String CUSTOMER_ID = "customer_id";

    public static final String COMMODITY = "commodity";

    public static final String STATUS = "status";

    public static final String DEDUCT_INTEGRAL = "deduct_integral";

    public static final String REMARKS = "remarks";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
