package org.launchcode.nextchapter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final WebClient

    public SearchController()

    public String getSearchResults(@RequestParam String query) {

        return "search";
    }
}
