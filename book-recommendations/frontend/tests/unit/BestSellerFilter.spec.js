import {shallowMount} from "@vue/test-utils";
import BestSellerFilter from "@/components/home/BestSellerFilter";
import books from "./data/Books";

function createComponentWrapper({...props} = {}) {
  const bestSellers = [
    {
      list_id: 1,
      list_name: "Hardcover Fiction",
      books: books
    },
    {
      list_id: 2,
      list_name: "Hardcover Nonfiction",
      books: books.slice(0, 3)
    }
  ];
  const wrapper = shallowMount(BestSellerFilter, {
    propsData: {
      bestSellerCategories: bestSellers,
      ...props,
    },
  });

  return wrapper;
}

describe("BestSellerFilter", () => {

  describe("component methods", () => {
    it("removeChip", () => {
      const wrapper = createComponentWrapper();
      const item = {
        text: 'Hardcover Fiction',
        value: 1
      }

      //first we need to make sure we select something from the dropdown (this selects the first item)
      wrapper.find("v-select-stub").vm.$emit("input", [1]);
      expect(wrapper.vm.selectedCategories).toEqual([1]) //this is an array of ids

      wrapper.vm.removeChip(item); //each item has a text and value property
      expect(wrapper.vm.selectedCategories).toEqual([]);

    });

    it("getChipColor", () => {
      const wrapper = createComponentWrapper();
      const itemOne = {
        text: 'Hardcover Fiction',
        value: 1
      }
      const itemTwo = {
        text: 'Hardcover Nonfiction',
        value: 2
      }

      wrapper.find("v-select-stub").vm.$emit("input", [1, 2]);
      expect(wrapper.vm.getChipColor(itemOne)).toBe("red lighten-4");
      expect(wrapper.vm.getChipColor(itemTwo)).toBe("pink lighten-4");
    });

    it("onChange - emits selected categories", () => {
      const wrapper = createComponentWrapper();

      //first select the items
      wrapper.find("v-select-stub").vm.$emit("input", [1, 2]);

      //then emit the event
      wrapper.find("v-select-stub").vm.$emit("change");

      //asserts that this component emited an event with the selected category ideas
      expect(wrapper.emitted().change[0]).toEqual([[1, 2]]);
    });
  });

  describe("computed properties", () => {
    it("items - correctly maps best seller prop to items for v-select", () => {
      const wrapper = createComponentWrapper();
      expect(wrapper.vm.items).toEqual([
        {
          text: "Hardcover Fiction",
          value: 1
        },
        {
          text: "Hardcover Nonfiction",
          value: 2
        }
      ]);
    });
  });
});
