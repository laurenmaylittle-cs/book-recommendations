package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.Collection;
import com.bestreads.bookrecommendations.helper.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/private/bookshelf/collections")
public class CollectionsController {

    private final CollectionsService collectionsService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @GetMapping
    public Set<Collection> getCollections(JwtAuthenticationToken authenticationToken) {
        var userId = AuthHelper.getUserId(authenticationToken);
        return collectionsService.getCollections(userId);
    }
}
