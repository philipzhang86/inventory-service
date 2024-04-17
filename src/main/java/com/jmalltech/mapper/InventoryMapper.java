package com.jmalltech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jmalltech.entity.Inventory;

import java.util.List;

/**
* @author philipzhang
* @description 针对表【mwms_inventory】的数据库操作Mapper
* @createDate 2024-04-16 09:55:19
* @Entity com.jmalltech.entity.Inventory
*/
public interface InventoryMapper extends BaseMapper<Inventory> {

    Inventory selectBySku(String sku);

    Inventory selectByProductName(String productName);

    List<Inventory> selectByClientId(Long clientId);

    Inventory selectByProductId(Long productId);
}




