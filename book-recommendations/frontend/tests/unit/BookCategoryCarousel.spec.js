import Vuetify from "vuetify";
import {mount} from "@vue/test-utils";
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";
import BookItem from "@/components/home/BookItem";
import books from "./data/Books";

function createComponentWrapper({breakpoint, ...props} = {}) {
  let vuetify = new Vuetify();
  let breakPointMock = {
    init: jest.fn(),
    framework: {},
    ...breakpoint,
  }

  vuetify.framework.breakpoint = breakPointMock;

  return mount(BookCategoryCarousel, {
    vuetify,
    propsData: {
      books,
      bestSellerCategory: "Best Seller",
      ...props,
    },
  });
}

describe("BookCategoryCarousel", () => {

  describe(
    "renders the different number of books on carousel based on screen size",
    () => {

      it("renders 5 for xl", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            xl: true,
          },
        });

        expect(wrapper.findAllComponents(BookItem).length).toBe(5);
      });

      it("renders 4 for lg", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            lg: true,
          },
        });

        expect(wrapper.findAllComponents(BookItem).length).toBe(4);
      });

      it("renders 3 for md", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            md: true,
          },
        });

        expect(wrapper.findAllComponents(BookItem).length).toBe(3);
      });

      it("renders 2 by default", () => {
        const wrapper = createComponentWrapper();

        expect(wrapper.findAllComponents(BookItem).length).toBe(2);
      });
    });
});
