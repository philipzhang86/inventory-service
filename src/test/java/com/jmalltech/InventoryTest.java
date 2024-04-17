package com.jmalltech;

import com.jmalltech.entity.Inventory;
import com.jmalltech.service.InventoryDomainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InventoryTest {
    @Autowired
    private InventoryDomainService service;

    @Test
    public void testInsert() {
        // insert test
        /*Inventory inventory = new Inventory();
        inventory.setClientId(1L);
        inventory.setSku("A0001");
        inventory.setProductName("Macbook Pro 2024");
        inventory.setQuantity(1);
        inventory.setProductId(1L);
        service.insert(inventory);*/
        System.out.println(service.getBySku("A0001"));
        System.out.println(service.getByProductName("Macbook Pro 2024"));
        System.out.println(service.getById(1L));
        /*System.out.println(service.getInventoryList());
        System.out.println(service.getListByClientId(1L));*/
    }

    @Test
    public void testUpdate(){
        Inventory i = service.getBySku("A0001");
        i.setQuantity(2);
        service.update(i);

    }

    @Test
    public void testRemove() {
        /*Inventory i = new Inventory();
        i.setClientId(10L);
        i.setSku("A0008");
        i.setProductName("Macbook Air 2024");
        i.setQuantity(1);
        i.setProductId(10L);
        service.insert(i);
        System.out.println(service.getById(10L));*/
        //System.out.println(service.getById(2L));
        System.out.println(service.remove(2L));
    }


    @Test
    public void testWebClient() {
        service.updateInventoryUsingAsn(1L).block();
    }
}
