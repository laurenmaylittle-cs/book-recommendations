import {getBestSellers} from '@/api/home-page-api-calls';
import HomePage from "@/pages/HomePage";
import books from "../data/Books";
import {shallowMount} from "@vue/test-utils";
import bestSellerFilter from "@/components/home/BestSellerFilter";

jest.mock('@/api/home-page-api-calls');

async function createComponentWrapper({props} = {}) {
  const wrapper = shallowMount(HomePage, {
    propsData: {
      ...props,
    }
  });

  await wrapper.vm.$nextTick(); //DOM updates in the next tick
  return wrapper;
}

describe('HomePage', () => {
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

  beforeEach(() => {
    jest.clearAllMocks();
    getBestSellers.mockImplementation(() => Promise.resolve(bestSellers));
  });

  it("'Gets the best sellers", async () => {
    const wrapper = await createComponentWrapper();

    expect(wrapper.vm.isLoading).toBe(false);
    expect(wrapper.vm.categories).toStrictEqual(bestSellers);
  });

  describe("component methods", () => {
    it("updateBestSellerList", async () => {
      const filteredBestSellers = [2];
      const wrapper = await createComponentWrapper();

      expect(wrapper.vm.selectedCategories).toStrictEqual([]);
      await wrapper.findComponent(bestSellerFilter).vm.$emit('change',
        filteredBestSellers);
      expect(wrapper.vm.selectedCategories).toStrictEqual(filteredBestSellers);

    });
  });

  describe("computed properties", () => {
    describe("filteredCategories", () => {
      it("returns all categories when selectedCategories is empty",
        async () => {
          const wrapper = await createComponentWrapper();

          expect(wrapper.vm.categories).toStrictEqual(bestSellers);
        });

      it("returns selected categories when selectedCategories is not empty",
        async () => {
          const filteredBestSellers = [2];
          const wrapper = await createComponentWrapper();

          await wrapper.findComponent(bestSellerFilter).vm.$emit('change',
            filteredBestSellers);
          expect(wrapper.vm.filteredCategories).toStrictEqual([{
            list_id: 2,
            list_name: "Hardcover Nonfiction",
            books: books.slice(0, 3)
          }]);
        });
    });
  });
});



