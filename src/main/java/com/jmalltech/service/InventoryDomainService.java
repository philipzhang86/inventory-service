package com.jmalltech.service;

import com.jmalltech.client.AsnServiceClient;
import com.jmalltech.entity.AsnItem;
import com.jmalltech.entity.Inventory;
import com.jmalltech.entity.Product;
import com.jmalltech.mapper.InventoryMapper;
import com.jmalltech.repository.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryDomainService {
    private InventoryService service;
    private InventoryMapper mapper;
    private CacheManager cacheManager;
  /*  private ObjectMapper objectMapper = new ObjectMapper();
    private WebClient webClient = WebClient.create();
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void receiveAsnMessage(String message) throws Exception {
        Inventory inventory = objectMapper.readValue(message, Inventory.class);
        service.save(inventory);
    }*/

    @Autowired
    private AsnServiceClient asnServiceClient;

    public Mono<Void> updateInventoryUsingAsn(Long asnId) {
        return asnServiceClient.getAsnItemsByAsnId(asnId)
                .flatMapMany(Flux::fromIterable)
                .flatMap(asnItem ->
                        asnServiceClient.getProductById(asnItem.getProductId())
                                .publishOn(Schedulers.boundedElastic())
                                .flatMap(product -> updateInventory(asnItem, product))
                )
                .then();
    }

    private Mono<Void> updateInventory(AsnItem asnItem, Product product) {
        return Mono.fromCallable(() -> {
            Inventory inventory = mapper.selectByProductId(asnItem.getProductId());
            if (inventory == null) {
                inventory = new Inventory();
                inventory.setProductId(asnItem.getProductId());
                inventory.setClientId(product.getClientId());
                inventory.setProductName(product.getName());
                inventory.setSku(product.getSku());
                inventory.setQuantity(asnItem.getQuantity());
                service.save(inventory);
            } else {
                inventory.setQuantity(inventory.getQuantity() + asnItem.getQuantity());
                service.updateById(inventory);
            }
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

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
