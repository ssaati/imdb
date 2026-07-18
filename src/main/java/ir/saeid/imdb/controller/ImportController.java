package ir.saeid.imdb.controller;

import ir.saeid.imdb.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("import")
@RestController
public class ImportController{
    @Autowired
    ImdbService imdbService;
    @GetMapping("all")
    public void importAll(){
        imdbService.importAllImdbFiles();
    }
}
