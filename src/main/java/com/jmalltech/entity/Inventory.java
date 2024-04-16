package com.jmalltech.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName mwms_inventory
 */
@TableName(value = "mwms_inventory", schema = "public")
@Getter
@Setter
@ToString
public class Inventory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long clientId;

    private Long productId;

    private String productName;

    private String sku;

    private Integer quantity;

    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    @TableField(value = "updated_date", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDate;

    private static final long serialVersionUID = 1L;
}