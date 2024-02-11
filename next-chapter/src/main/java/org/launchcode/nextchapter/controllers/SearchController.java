package org.launchcode.nextchapter.controllers;

import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.models.Blog;
import org.launchcode.nextchapter.models.Club;
import org.launchcode.nextchapter.models.SearchResult;
import org.launchcode.nextchapter.models.SearchResultBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//guided by Java Brains on YT: https://www.youtube.com/watch?v=6K0im9vcoCk (MH)

@Controller
public class SearchController {


    @Autowired
    private ClubRepository clubRepository;

    //WebClient -- included within the dependency WebFlux -- seems to be the most current tool within SpringBoot to handle HTTP calls.
    private final WebClient webClient;

    //passing in this builder argument (seen below) allows the method to build a Web Client (MH)
    public SearchController(WebClient.Builder webClientBuilder) {

// note: what is commented out directly below is the original code: does not work, because it exceeds buffer limits
//        this.webClient = webClientBuilder
//                .baseUrl("https://openlibrary.org/search.json").build();

        //This builds a Web Client which is able to make a call to the included URL (MH)
        this.webClient = webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))

                .build()).baseUrl("https://openlibrary.org/search.json").build();
    }

    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, @RequestParam int clubId, Model model) {
        //this builds a single GET request
        //fyi: Mono is a class object, as almost a placeholder for a future singular object(???)
        Mono<SearchResult> resultsMono = this.webClient.get()
                .uri("?q={query}", query)
                //the uri above comes directly from the search URI of the external API (MH)
                .retrieve().bodyToMono(SearchResult.class);
        SearchResult result = resultsMono.block();
        List<SearchResultBook> books = result.getDocs()
                .stream()
                .limit(5)
                //this, above, limits the results to only showing 5 of the most relevant search results.
                .collect(Collectors.toList());

        model.addAttribute("searchResults", books);
        model.addAttribute("clubId", clubId);

        return "search";
    }

    @PostMapping("search")
    public String processSearchResults(@RequestParam int clubId, @RequestParam String coverId, Model model) {

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();
        club.setCoverId(coverId);

        //This is the part Bekah was missing!! UPDATE the repository with the change to the club.
        clubRepository.save(club);
        List<Blog> blogPosts = club.getBlogPosts();
        Collections.reverse(blogPosts);
        model.addAttribute("club", club);
        model.addAttribute("title", club.getDisplayName());
        model.addAttribute("blogs", blogPosts);


//        if (clubId != null) {
//            Optional<Club> result = clubRepository.findById(clubId);
//            Club club = result.get();
        //what is even the plan, below. girl.
//        Optional<Club> result = clubRepository.findById(1);
//        Club club = result.get();
//        Optional<Club> clubResult = clubRepository.findById(clubId);
//        Club club = clubResult.get();
//        club.setCoverId(coverId);
//        "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg"

        return "redirect:clubs/detail?clubId=" + clubId;
    }

//        hey molly
    //take a look at these and see if you could just save the coverID into its own repo
//        @GetMapping("create")
//    public String displayCreateUserForm(Model model) {
//        model.addAttribute("title", "Create Member");
//        model.addAttribute(new Member());
//        return "members/create";
//    }
//
//    @PostMapping("create")
//    public String processCreateUserForm(@ModelAttribute Member newMember, Errors errors, Model model){
//        memberRepository.save(newMember);
//        return "redirect:/members";
//    }

}
