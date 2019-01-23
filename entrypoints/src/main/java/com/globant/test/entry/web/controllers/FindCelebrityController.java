package com.globant.test.entry.web.controllers;

import com.globant.findcelebrities.entities.Person;
import com.globant.test.exceptions.CelebrityNotFound;
import com.globant.test.usescases.FindCelebrityUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/celebrity")
public class FindCelebrityController {

    private final FindCelebrityUseCase useCase;

    @Autowired
    public FindCelebrityController(final FindCelebrityUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/search")
    public Person findCelebrity() throws CelebrityNotFound {
        return useCase.findCelebrity();
    }
}
