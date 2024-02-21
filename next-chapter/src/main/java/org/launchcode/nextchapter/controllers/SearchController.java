package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.*;
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

    @Autowired
    private MemberRepository memberRepository;

    //The method is posted in each Controller
    public Member getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");
        if (userId == null) {
            return null;
        }
        Optional<Member> user = memberRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

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
    public String getSearchResults(@RequestParam String query, @RequestParam int clubId, Model model,
                                   HttpSession session) {
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
        model.addAttribute("member", getUserFromSession(session));

        return "search";
    }

    @PostMapping("search")
    public String processSearchResults(@RequestParam int clubId, @RequestParam String coverId, @RequestParam String activeBook, Model model) {

        Optional<Club> result = clubRepository.findById(clubId);
        Club club = result.get();
        club.setCoverId(coverId);
        club.setActiveBook(activeBook);

        //This is the part Bekah was missing!! UPDATE the repository with the change to the club.
        clubRepository.save(club);
        List<Blog> blogPosts = club.getBlogPosts();
        Collections.reverse(blogPosts);
        model.addAttribute("club", club);
        model.addAttribute("title", club.getDisplayName());
        model.addAttribute("blogs", blogPosts);

        return "redirect:clubs/detail?clubId=" + clubId;
    }
}
