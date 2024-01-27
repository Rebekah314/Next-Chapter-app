package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.models.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//guided by Java Brains on YT: https://www.youtube.com/watch?v=6K0im9vcoCk (MH)

@Controller
public class SearchController {

    private final WebClient webClient;

    //passing in this argument below allows the method to build a Web Client (MH)
    public SearchController(WebClient.Builder webClientBuilder) {
        //below builds a Web Client which is able to make a call to the included URL (MH)
        this.webClient = webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl("https://openlibrary.org/search.json").build();
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model) {
        //this builds a single GET request
        //fyi: Mono is a class object, as almost a placeholder for a future singular object(???)
        Mono<SearchResult> resultsMono = this.webClient.get()
                .uri("?q={query}", query)
                //the uri above comes directly from the search URI of the external API (MH)
                .retrieve().bodyToMono(SearchResult.class);
        SearchResult result = resultsMono.block();
        model.addAttribute("searchResult", result);

        return "search";
    }
}
