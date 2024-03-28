package br.com.ebac.meme.ApiServices;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class ApiServiceCategoria {

    private WebClient webClient;

    public ApiServiceCategoria(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/memelandia").build();
    }

    public String fetchDataFromApi(){
        return webClient.get().uri("/categorias").retrieve().bodyToMono(String.class).block();

    }
}