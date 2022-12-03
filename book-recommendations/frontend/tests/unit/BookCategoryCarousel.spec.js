import Vuetify from "vuetify";
import {shallowMount} from "@vue/test-utils";
import BookCategoryCarousel from "@/components/home/BookCategoryCarousel";

function createComponentWrapper({breakpoint, ...props} = {}) {
  let vuetify = new Vuetify();
  let breakPointMock = {
    init: jest.fn(),
    framework: {},
    ...breakpoint,
  }

  vuetify.framework.breakpoint = breakPointMock;

  return shallowMount(BookCategoryCarousel, {
    vuetify,
    propsData: {
      books: [
        {
          id: 1,
          title: "THE LORD OF THE RINGS",
          imageLinks: {
            thumbnail: "https://google/com/image.jpg"
          },
        }
      ],
      bestSellerCategory: "Best Seller",
      ...props,
    },
  });
}

describe("BookCategoryCarousel", () => {
  describe("computed properties", () => {
    describe("getNumberOfBooks", () => {
      it("returns 5 when the screen size is xl", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            xl: true
          }
        });

        expect(wrapper.vm.getNumberOfBooks).toBe(5);
      });

      it("returns 4 when the screen size is lg", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            lg: true
          }
        });

        expect(wrapper.vm.getNumberOfBooks).toBe(4);
      });

      it("returns 3 when the screen size is md", () => {
        const wrapper = createComponentWrapper({
          breakpoint: {
            md: true
          }
        });

        expect(wrapper.vm.getNumberOfBooks).toBe(3);
      });

      it("returns 2 by default", () => {
        const wrapper = createComponentWrapper();

        expect(wrapper.vm.getNumberOfBooks).toBe(2);
      });
    });
  });
})
