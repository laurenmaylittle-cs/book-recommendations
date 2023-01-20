import {shallowMount} from '@vue/test-utils';
import {getBookInfo} from "@/api/view-book";
import ViewBookPage from "@/pages/ViewBookPage";
import books from "../data/Books";

jest.mock('@/api/view-book');

function createComponentWrapper({props} = {}) {
  return shallowMount(ViewBookPage, {
    propsData: {
      ...props,
    }
  });
}

it("Gets the book info", async () => {

  const bookInfo = books[0];

  getBookInfo.mockImplementation((isbn) => {
    if (isbn === '9780753827666') {
      return Promise.resolve(bookInfo); // when we call getBookInfo with isbn 9780753827666, return bookInfo
    } else {
      return Promise.resolve([]); // otherwise return an empty array
    }

  });

  const wrapper = await createComponentWrapper();
  await wrapper.vm.$nextTick();

  expect(wrapper.vm.isLoading).toBe(false);
  expect(wrapper.vm.bookData).toStrictEqual(bookInfo);
})


