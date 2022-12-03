import {shallowMount} from "@vue/test-utils";
import BookItem from "@/components/home/BookItem";

function createComponentWrapper({...props} = {}) {
  return shallowMount(BookItem, {
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
