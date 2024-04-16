package com.jmalltech.controller;

import com.jmalltech.entity.Inventory;
import com.jmalltech.helper.ResponseHelper;
import com.jmalltech.service.InventoryDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staffs/inventories")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {
    private InventoryDomainService inventoryService;

    @Autowired
    public InventoryController(InventoryDomainService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getById(id);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseHelper.notFoundResponse("Inventory not found for ID: " + id);
        }
    }


    @GetMapping("/by-sku/{sku}")
    public ResponseEntity<?> getInventoryBySku(@PathVariable String sku){
        Inventory inventory = inventoryService.getBySku(sku);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseHelper.notFoundResponse("Inventory not found for sku: " + sku);
        }
    }

    @GetMapping("/by-product-name/{productName}")
    public ResponseEntity<?> getInventoryByProductName(@PathVariable String productName){
        Inventory inventory = inventoryService.getByProductName(productName);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseHelper.notFoundResponse("Inventory not found for productName: " + productName);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Inventory> insertInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.insert(inventory);
        return ResponseEntity.ok(createdInventory);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateInventory(@RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.update(inventory);
        if (updatedInventory != null) {
            return ResponseEntity.ok(updatedInventory);
        } else {
            return ResponseHelper.notFoundResponse("Inventory not found ");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        boolean success = inventoryService.remove(id);
        if (success) {
            return ResponseEntity.ok().body("{\"message\": \"Inventory deleted successfully.\"}");
        } else {
            return ResponseHelper.notFoundResponse("Inventory not found for ID: " + id);
        }
    }
}
