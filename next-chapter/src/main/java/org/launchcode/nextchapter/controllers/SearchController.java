package org.launchcode.nextchapter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//guided by Java Brains on YT: https://www.youtube.com/watch?v=6K0im9vcoCk (MH)

@Controller
public class SearchController {

    private final WebClient webClient;

    //passing in this argument below allows the method to build a Web Client (MH)
    public SearchController(WebClient.Builder webClientBuilder) {
        //below builds a Web Client which is able to make a call to the included URL (MH)
        this.webClient = webClientBuilder
                .baseUrl("https://openlibrary.org/search.json").build();
    }

    public String getSearchResults(@RequestParam String query) {
        //this builds a single GET request
        //fyi: Mono is a class object, as almost a placeholder for a future singular object(???)
        Mono<String> foo = this.webClient.get()
                .uri("?q={query}", query)
                //the uri above comes directly from the search URI of the external API (MH)
                .retrieve().bodyToMono(String.class);
        String bookTitle = foo.block();
//Molly: using String is all just a placeholder until we create classes for the search results (which are arrays)
        return "search";
    }
}
