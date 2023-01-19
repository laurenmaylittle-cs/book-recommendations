import BookshelfPage from "@/pages/BookshelfPage.vue";
import {mount} from "@vue/test-utils";
import Vuetify from "vuetify";
import {getCollection, getCollectionsForUser} from "@/api/bookshelf-api-calls";
import flushPromises from "flush-promises";

jest.mock("@/api/bookshelf-api-calls");

async function createComponentWrapper({props} = {}) {
  let vuetify = new Vuetify();
  //vuetify looks for a data-app attribute on the body element which is not present in the test environment
  document.body.setAttribute("data-app", "true");
  const wrapper = mount(BookshelfPage, {
    vuetify,
    mocks: {
      $auth: {
        getTokenSilently: jest.fn().mockResolvedValue('token-001')
      }
    },
    propsData: {
      ...props,
    },
  });
  await wrapper.vm.$nextTick();
  await flushPromises();
  return wrapper;
}

describe("BookshelfPage", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    getCollectionsForUser.mockImplementation((id) => {
      if (id === 'token-001') {
        return Promise.resolve([
          {
            id: 1,
            name: "test-collection"
          }
        ]);
      }
    });
  });

  it("getCollections", async () => {
    const wrapper = await createComponentWrapper();
    expect(wrapper.vm.isLoading).toBe(false);
    expect(wrapper.vm.collections).toStrictEqual([
      {
        id: 1,
        name: "test-collection"
      }
    ]);
    expect(wrapper.vm.colors).toHaveLength(1);
    expect(wrapper.vm.colors[0]).toMatch(
      /^hsl\(\d{1,3},\s\d{1,3}%,\s\d{1,3}%\)$/);
  });

  it("getNewCollection", async () => {
    //Mimics how the parent component behaves when a new collection is created by a user
    const wrapper = await createComponentWrapper();
    getCollection.mockImplementation((path, token) => {
      if (path === '/bookshelf/collections/1' && token === 'token-001') {
        return Promise.resolve({
          id: 2,
          name: "new collection"
        });
      }
    });

    //current state before creating a new collection
    expect(wrapper.vm.collections).toStrictEqual([
      {
        id: 1,
        name: "test-collection"
      }
    ]);
    expect(wrapper.vm.colors).toHaveLength(1);
    wrapper.findComponent({ref: "createModal"}).findComponent(
      {ref: "createCollectionBtn"}).trigger("click"); //first we need to have the form open
    wrapper.findComponent({ref: "createModal"}).vm.$emit(
      "collection-created", "http://localhost:8080/bookshelf/collections/1");
    await flushPromises();
    await wrapper.vm.$nextTick();

    //state after the user creates a new collection
    expect(wrapper.vm.collections).toStrictEqual([
      {
        id: 1,
        name: "test-collection"

      },
      {
        id: 2,
        name: "new collection"
      }
    ]);
    expect(wrapper.vm.colors).toHaveLength(2);
  });
});
