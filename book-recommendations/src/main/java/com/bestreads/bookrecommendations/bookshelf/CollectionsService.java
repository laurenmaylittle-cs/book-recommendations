package com.bestreads.bookrecommendations.bookshelf;

import com.bestreads.bookrecommendations.bookshelf.model.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class CollectionsService {

    private final CollectionsRepository collectionsRepository;

    @Autowired
    CollectionsService(CollectionsRepository collectionsRepository) {
        this.collectionsRepository = collectionsRepository;
    }

    Set<Collection> getCollections(String userId) {
        return collectionsRepository.findByUserId(userId);
    }

}
