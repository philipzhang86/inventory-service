package com.jmalltech.service;

import com.jmalltech.entity.Inventory;
import com.jmalltech.mapper.InventoryMapper;
import com.jmalltech.repository.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryDomainService {
    private InventoryService service;
    private InventoryMapper mapper;
    private CacheManager cacheManager;

    @Autowired
    public InventoryDomainService(InventoryService service, InventoryMapper mapper, CacheManager cacheManager) {
        this.service = service;
        this.mapper = mapper;
        this.cacheManager = cacheManager;
    }

    public Inventory insert(Inventory inventory) {
        service.save(inventory);
        return inventory;
    }

    @Cacheable(value = "inventory", key = "#id.toString()", unless = "#result == null")
    public Inventory getById(Long id) {
        return service.getById(id);
    }

    @Cacheable(value = "inventory", key = "#sku", unless = "#result == null")
    public Inventory getBySku(String sku) {
        return mapper.selectBySku(sku);
    }

    @Cacheable(value = "inventory", key = "#productName", unless = "#result == null")
    public Inventory getByProductName(String productName) {
        return mapper.selectByProductName(productName);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "inventoryList", allEntries = true)},
            put = {
                    @CachePut(value = "inventory", key = "#inventory.id.toString()", unless = "#result == null"),
                    @CachePut(value = "inventory", key = "#inventory.sku", unless = "#result == null"),
                    @CachePut(value = "inventory", key = "#inventory.productName", unless = "#result == null")
            })
    public Inventory update(Inventory inventory) {
        boolean success = service.updateById(inventory);
        if (!success) return null;
        return inventory;
    }

    @Caching(evict = {
            @CacheEvict(value = "inventoryList", allEntries = true),
            @CacheEvict(value = "inventory", key = "#id.toString()")
    })
    public boolean remove(Long id) {
        Inventory inventory = service.getById(id);
        if (inventory != null) {
            Objects.requireNonNull(cacheManager.getCache("inventory")).evict(inventory.getSku());
            Objects.requireNonNull(cacheManager.getCache("inventory")).evict(inventory.getProductName());
            service.removeById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Inventory> getListByClientId(Long clientId) {
        return mapper.selectByClientId(clientId);
    }

    @Cacheable(value = "inventoryList", unless = "#result == null")
    public List<Inventory> getInventoryList() {
        return service.list();
    }
}
