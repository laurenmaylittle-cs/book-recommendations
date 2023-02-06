import CollectionItem from "@/components/bookshelf/CollectionItem.vue";
import {mount} from "@vue/test-utils";
import Vuetify from "vuetify";

function createComponentWrapper({breakpoint, props} = {}) {
  let vuetify = new Vuetify();
  let breakPointMock = {
    init: jest.fn(),
    framework: {},
    ...breakpoint,
  }

  vuetify.framework.breakpoint = breakPointMock;

  return mount(CollectionItem, {
    vuetify,
    propsData: {
      collectionName: "test collection",
      ...props,
    },
  });
}

//Trivial tests but added them nonetheless - tests the number of columns rendered based on screen size
describe("CollectionItems", () => {
  describe("number of columns based on screen size", () => {
    it("2 for xl", async () => {
      const wrapper = createComponentWrapper({
        breakpoint: {
          xl: true,
        },
      });
      await wrapper.vm.$nextTick();

      expect(wrapper.html()).toContain("col-2");
    });

    it("3 for lg", () => {
      const wrapper = createComponentWrapper({
        breakpoint: {
          lg: true,
        },
      });

      expect(wrapper.html()).toContain("col-3");
    });

    it("4 for md", () => {
      const wrapper = createComponentWrapper({
        breakpoint: {
          md: true,
        },
      });

      expect(wrapper.html()).toContain("col-4");
    });

    it("5 by default", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.html()).toContain("col-5");
    });
  });

  describe("component methods", () => {
    it("getHoverEffect", () => {
      const wrapper = createComponentWrapper();

      expect(wrapper.vm.getHoverEffect(true)).toBe("white");
      expect(wrapper.vm.getHoverEffect(false)).toBe("#26c6da");
    });
  });
});
