package com.jmalltech.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmalltech.entity.Inventory;
import com.jmalltech.repository.InventoryService;
import com.jmalltech.mapper.InventoryMapper;
import org.springframework.stereotype.Service;

/**
* @author philipzhang
* @description 针对表【mwms_inventory】的数据库操作Service实现
* @createDate 2024-04-16 09:55:19
*/
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory>
    implements InventoryService{

}




