package org.launchcode.nextchapter.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.nextchapter.data.ClubRepository;
import org.launchcode.nextchapter.data.MemberRepository;
import org.launchcode.nextchapter.models.Member;
import org.launchcode.nextchapter.models.QuoteResult;
import org.launchcode.nextchapter.models.SearchResult;
import org.launchcode.nextchapter.models.SearchResultBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/* Created by Rebekah Garris */

@Controller
@SuppressWarnings("unchecked")
public class HomeController {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    MemberRepository memberRepository;



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



    private String fetchQuote() {
        //ZenQuotes API documentation here:
        //https://docs.zenquotes.io/zenquotes-documentation/

        //returns an arraylist of quotes. In each quote are
        // q(quote text), a(author name), and h(html text)
        //Need to make a model to store these: QuoteResult.

//        WebClient client = WebClient.create("https://zenquotes.io/api/random/");
//
//        Mono<List> result = client.get()
//                .retrieve()
//                .bodyToMono(List.class);
//        List<QuoteResult> quoteList = result.block();


        return "Do or do not, there is not try";
    }


    @GetMapping
    public String index(Model model) {

        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model, HttpSession session) {


        model.addAttribute("quote", fetchQuote());
        model.addAttribute("title", "Welcome to your Next Chapter!");
        model.addAttribute("clubs", clubRepository.findAll());
        model.addAttribute("member", getUserFromSession(session));

        return "index";
    }
}
