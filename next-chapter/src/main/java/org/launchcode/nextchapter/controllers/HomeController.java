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

    private final WebClient webClient;

    public HomeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://zenquotes.io/api/random").build();
        //ZenQuotes API documentation here:
        //https://docs.zenquotes.io/zenquotes-documentation/
    }

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


    private QuoteResult fetchQuote() {

        Mono<QuoteResult[]> foo = this.webClient.get()
                .retrieve().bodyToMono(QuoteResult[].class);
        QuoteResult[] quoteList = foo.block();
        //QuoteResult quote = quoteList.get(0);

        //returns an arraylist of quotes. In each quote are
        // q(quote text), a(author name), and h(html text)

        return quoteList[0];
    }

//    private String fetchRandomQuote(QuoteResult[] quoteList) {
//        Random rand = new Random();
//        // Obtain a number between [0 - 49].
//        int n = rand.nextInt(50);
//
//        return quoteList[n].getQ();
//    }


    @GetMapping
    public String index(Model model) {

        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model, HttpSession session) {

        QuoteResult quote = fetchQuote();

        model.addAttribute("quote", quote.getQ());
//                [0].getQ());
        model.addAttribute("author", quote.getA());

//                fetchRandomQuote(fetchQuotes()));
        model.addAttribute("title", "Welcome to your Next Chapter!");
        model.addAttribute("clubs", clubRepository.findAll());
        model.addAttribute("member", getUserFromSession(session));

        return "index";
    }
}
