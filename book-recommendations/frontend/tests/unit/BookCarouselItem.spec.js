import {shallowMount} from "@vue/test-utils";
import BookItem from "@/components/home/BookItem";
import Vuetify from "vuetify";

function createComponentWrapper({props} = {}) {
  const breakpoint = {
    xs: false,
    sm: false,
    md: false,
    lg: false,
    xl: true,
  };
  let vuetify = new Vuetify();
  let breakPointMock = {
    init: jest.fn(),
    framework: {},
    ...breakpoint,
  }

  vuetify.framework.breakpoint = breakPointMock;
  return shallowMount(BookItem, {
    vuetify,
    propsData: {
      bookImageLink: "https://google/com/image.jpg",
      bookTitle: "THE LORD OF THE RINGS",
      ...props,
    },
  });
}

describe("BookCarouselItem component methods", () => {
  it("getUpdatedTitle", () => {
    const wrapper = createComponentWrapper();

    expect(wrapper.vm.getUpdatedTitle("THE LORD OF THE RINGS")).toBe(
      "The Lord of the Rings");
  });

  describe("getHoverEffect", () => {
    it("blue grey when true", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.vm.getHoverEffect(true)).toBe("blue-grey lighten-4");
    });

    it("transparent when false", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.vm.getHoverEffect(false)).toBe("transparent");
    });
  });
});
