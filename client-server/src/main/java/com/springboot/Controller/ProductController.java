package com.springboot.Controller;

import com.springboot.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
public class ProductController {

    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/products-view")
    public List<Product> getProducts() {
        return this.webClient
                .get()
                .uri("http://localhost:8090/products")
                .attributes(clientRegistrationId("products-client"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                .block();
    }
}