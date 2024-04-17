package com.jmalltech.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@TableName(value = "mwms_product", schema = "public")
@Getter
@Setter
@ToString
public class Product implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long clientId;

    private String sku;

    private String currency;

    private BigDecimal sellingPrice;

    private String shortDescription;

    private Long createdById;

    private Long updatedById;

    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    @TableField(value = "updated_date", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDate;

    private static final long serialVersionUID = 1L;

    public void setSellingPriceFromDouble(Double price) {
        this.sellingPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }

}
