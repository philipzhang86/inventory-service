package com.jmalltech.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "mwms_asn_item", schema = "public")
@Getter
@Setter
@ToString
public class AsnItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long asnId;

    private Long productId;

    private String skuId;

    private Integer quantity;

    private String asnItemName;

    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    @TableField(value = "updated_date", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDate;

    private static final long serialVersionUID = 1L;

}
