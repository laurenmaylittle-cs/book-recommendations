package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.book.BookDAO;
import com.bestreads.bookrecommendations.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/private/bookshelf/books")
public class IndividualBookshelfController {
    private final CollectionsService collectionsService;

    @Autowired
    public IndividualBookshelfController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @GetMapping
    public List<BookDAO> getBooksInCollection(JwtAuthenticationToken authenticationToken,
                                              @Param("bookshelfId") Long bookshelfId) {
        var userId = getUserIdOrBadRequest(authenticationToken);

        return collectionsService.getBooksInCollectionByIdAndUserOrBadRequest(bookshelfId, userId);
    }

    @PostMapping("/delete")
    public void deleteBookInCollection(JwtAuthenticationToken authenticationToken,
                                       @RequestParam Long bookshelfId,
                                       @RequestParam List<Long> bookIds) {
        var userId = getUserIdOrBadRequest(authenticationToken);
        if (!collectionsService.collectionBelongsToUser(userId, bookshelfId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No collection for user found");
        }

        collectionsService.deleteBooksFromCollection(bookshelfId, bookIds);
    }

    private String getUserIdOrBadRequest(JwtAuthenticationToken authenticationToken) {
        return AuthUtils.getUserId(authenticationToken).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID found in token");
        });
    }
}
