package ir.saeid.imdb.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "import IMDB files into memory")
    @GetMapping("all")
    public void importAll(){
        imdbService.importAllImdbFiles();
    }
}
