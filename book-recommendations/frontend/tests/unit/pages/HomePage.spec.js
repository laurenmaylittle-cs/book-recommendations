import {shallowMount} from '@vue/test-utils';
import {getBestSellers} from '@/api/home-page-api-calls';
import HomePage from "@/pages/HomePage";

jest.mock('@/api/home-page-api-calls');

function createComponentWrapper({...props} = {}) {
  return shallowMount(HomePage, {
    propsData: {
      ...props,
    }
  });
}

it("'Gets the best sellers", async () => {

  const bestSellers = [
    {
      "list_id": 1,
      "list_name": "Fiction Hardcover",
      "books": [
        {
          "title": "The Fellowship of the Ring",
          "imageLinks": {
            "smallThumbnail": null,
            "thumbnail": "https://storage.googleapis.com/some-image.jpg"
          },
        }
      ]
    }
  ];

  getBestSellers.mockImplementation(() => Promise.resolve(bestSellers));

  const wrapper = await createComponentWrapper();

  expect(wrapper.vm.isLoading).toBeFalsy();
  expect(wrapper.vm.categories).toStrictEqual(bestSellers);
})


