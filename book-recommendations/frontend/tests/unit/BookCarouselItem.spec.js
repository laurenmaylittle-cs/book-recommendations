import {mount} from "@vue/test-utils";
import BookCarouselItem from "@/components/home/BookCarouselItem";

function createComponentWrapper({...props} = {}) {
  return mount(BookCarouselItem, {
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

    expect(wrapper.vm.getUpdatedTitle("THE LORD OF THE RINGS")).toBe("The Lord of the Rings");
  });

  describe("getHoverEffect", () => {
    it("blue grey when true", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.vm.getHoverEffect(true)).toBe("blue-grey lighten-4");
    });

    it("white when false", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.vm.getHoverEffect(false)).toBe("white");
    });
  });
});
