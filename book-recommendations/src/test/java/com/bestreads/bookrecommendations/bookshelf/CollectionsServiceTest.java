package com.bestreads.bookrecommendations.bookshelf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestreads.bookrecommendations.bookshelf.json.CollectionJson;
import com.bestreads.bookrecommendations.bookshelf.model.CollectionDAO;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectionsServiceTest {

  @Mock
  private CollectionsRepository collectionsRepository;

  @InjectMocks
  private CollectionsService collectionsService;


  private final String userId = "USER_ID1234";
  private CollectionDAO collectionDAOOne;
  private CollectionDAO collectionDAOTwo;

  @BeforeEach
  void setUp() {
    collectionDAOOne = new CollectionDAO();
    collectionDAOOne.setId(1L);
    collectionDAOOne.setName("Collection One");
    collectionDAOOne.setUserId(userId);

    collectionDAOTwo = new CollectionDAO();
    collectionDAOTwo.setId(2L);
    collectionDAOTwo.setName("Collection Two");
    collectionDAOTwo.setUserId(userId);
  }

  @Test
  void getCollections() {
    when(collectionsRepository.findByUserId(userId)).thenReturn(
        Set.of(collectionDAOOne, collectionDAOTwo));

    assertEquals(
        Set.of(new CollectionJson(1L, "Collection One"), new CollectionJson(2L, "Collection Two")),
        collectionsService.getCollections(userId));
  }

  @Test
  void getCollectionById_whenValidIdProvided() {
    when(collectionsRepository.findById(1L)).thenReturn(Optional.of(collectionDAOOne));

    assertEquals(collectionDAOOne, collectionsService.getCollectionById(1L));
  }

  @Test
  void getCollectionById_whenInvalidIdProvided() {
    when(collectionsRepository.findById(400L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> collectionsService.getCollectionById(400L),
        "Collection with ID:400 not found");
  }

  @Test
  void createNewCollection() {
    ArgumentCaptor<CollectionDAO> collectionArgumentCaptor = ArgumentCaptor.forClass(
        CollectionDAO.class);

    collectionsService.createNewCollection(userId, "Collection Spring");
    verify(collectionsRepository).save(collectionArgumentCaptor.capture());

    assertEquals("Collection Spring", collectionArgumentCaptor.getValue().getName());
    assertEquals(userId, collectionArgumentCaptor.getValue().getUserId());
  }
}
