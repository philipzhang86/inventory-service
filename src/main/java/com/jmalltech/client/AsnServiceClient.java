package com.jmalltech.client;

import com.jmalltech.entity.Asn;
import com.jmalltech.entity.AsnItem;
import com.jmalltech.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

import java.util.List;

@Component
public class AsnServiceClient {
    private WebClient webClient;

    @Autowired
    public AsnServiceClient(WebClient.Builder webClientBuilder) {
        // 直接硬编码URL
        this.webClient = webClientBuilder.baseUrl("http://localhost:9081/asn-service").build();
    }

    public Mono<Asn> getAsnById(Long asnId) {
        return this.webClient.get()
                .uri("/internal/by-asn-id/{asnId}", asnId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Asn.class);
    }

    public Mono<AsnItem> getAsnItemById(Long asnItemId) {
        return this.webClient.get()
                .uri("/internal/by-asn-item-id/{asnItemId}", asnItemId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AsnItem.class);
    }

    public Mono<Product> getProductById(Long productId) {
        return this.webClient.get()
                .uri("/internal/by-product-id/{productId}", productId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class);
    }

    public Mono<List<AsnItem>> getAsnItemsByAsnId(Long asnId) {
        return this.webClient.get()
                .uri("/internal/items-by-asn-id/{asnId}", asnId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(AsnItem.class)
                .collectList();
    }
}
