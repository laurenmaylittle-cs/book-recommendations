package com.bestreads.bookrecommendations.awspersonalize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
class AwsPersonalizeService {

    private final BookEntityRepository bookEntityRepository;

    @Autowired
    AwsPersonalizeService(BookEntityRepository bookEntityRepository){this.bookEntityRepository = bookEntityRepository;}

    public ArrayList<String> getRecs(PersonalizeRuntimeClient personalizeRuntimeClient, String campaignArn, String itemId){
        ArrayList<String> isbns = new ArrayList<>();
        try {
            GetRecommendationsRequest recommendationsRequest = GetRecommendationsRequest.builder()
                    .campaignArn(campaignArn)
                    .numResults(20)
                    .itemId(itemId)
                    .build();
            GetRecommendationsResponse recommendationsResponse = personalizeRuntimeClient.getRecommendations(recommendationsRequest);
            List<PredictedItem> items = recommendationsResponse.itemList();

            for (PredictedItem item: items) {
                System.out.println("Item Id is : "+item.itemId());
                isbns.add(item.itemId());
            }

        } catch (AwsServiceException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return isbns;
    }
    @Transactional
    public void addBookToDb(String isbn, String title, String author, String genre, String publisher, String thumbnail) {
        var book = new BookEntity(isbn, title, author, genre, publisher, thumbnail);
        bookEntityRepository.save(book);
    }


}
