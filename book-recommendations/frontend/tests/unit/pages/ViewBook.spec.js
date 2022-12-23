import {shallowMount} from '@vue/test-utils';
import ViewBook from "@/pages/ViewBook";
import {getBookInfo} from "@/api/view-book";

jest.mock('@/api/view-book');

function createComponentWrapper({...props} = {}) {
  return shallowMount(ViewBook, {
    propsData: {
      ...props,
    }
  });
}

it("Gets the book info", async () => {

  const bookInfo =
    {
      "title": "Gone Girl",
      "authors": [
        "Gillian Flynn"
      ],
      "publisher": "Hachette UK",
      "publishedDate": "2012-05-24",
      "description": "THE ADDICTIVE No.1 BESTSELLER AND INTERNATIONAL PHENOMENON OVER 20 MILLION COPIES SOLD WORLDWIDE THE BOOK THAT DEFINES PSYCHOLOGICAL THRILLER Who are you? What have we done to each other? These are the questions Nick Dunne finds himself asking on the morning of his fifth wedding anniversary, when his wife Amy suddenly disappears. The police suspect Nick. Amy's friends reveal that she was afraid of him, that she kept secrets from him. He swears it isn't true. A police examination of his computer shows strange searches. He says they weren't made by him. And then there are the persistent calls on his mobile phone. So what really did happen to Nick's beautiful wife? 'Flynn is a brilliantly accomplished psychological crime writer and this latest book is so dark, so twisted and so utterly compelling that it actually messes with your mind' DAILY MAIL 'A near-masterpiece. Flynn is an extraordinary writer who, with every sentence, makes words do things that other writers merely dream of' SOPHIE HANNAH, Sunday Express 'You think you're reading a good, conventional thriller and then it grows into a fascinating portrait of one averagely mismatched relationship...Nothing's as it seems - Flynn is a fabulous plotter, and a very sharp observer of modern life in the aftermath of the credit crunch' THE TIMES 'One of the most popular thrillers of the year is also one of the smartest... Flynn's book cleverly outpaces its neo-noir trappings and consistently surprises the reader.' FINANCIAL TIMES",
      "pageCount": 320,
      "categories": [
        "Fiction"
      ],
      "imageLinks": {
        "smallThumbnail": "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
        "thumbnail": "http://books.google.com/books/content?id=hxL2qWMAgv8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
      }
      , "language": "en",
      "averageRating": 3,
      "ratingsCount": 963
    };

  getBookInfo('9780753827666').mockImplementation(
    () => Promise.resolve(bookInfo));

  const wrapper = await createComponentWrapper();

  expect(wrapper.vm.isLoading).toBe(false);
  expect(wrapper.vm.bookData).toStrictEqual(bookInfo);
})


