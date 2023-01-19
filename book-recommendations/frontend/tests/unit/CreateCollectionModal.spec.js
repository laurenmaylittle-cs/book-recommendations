import CreateCollectionModal
  from "@/components/bookshelf/CreateCollectionModal";
import Vuetify from "vuetify";
import {mount} from "@vue/test-utils";
import {createNewCollection} from "@/api/bookshelf-api-calls";
import flushPromises from "flush-promises";

jest.mock("@/api/bookshelf-api-calls");

async function createComponentWrapper({props} = {}) {
  let vuetify = new Vuetify();
  //vuetify looks for a data-app attribute on the body element which is not present in the test environment
  document.body.setAttribute("data-app", "true");
  const wrapper = mount(CreateCollectionModal, {
    vuetify,
    propsData: {
      ...props,
    },
  });
  wrapper.vm.$auth = {getTokenSilently: jest.fn().mockResolvedValue('my-token')}
  await wrapper.vm.$nextTick();
  return wrapper;
}

describe("CreateCollectionModal", () => {

  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe("component methods", () => {
    it("createCollection", async () => {
      //Mimics the user entering a collection name and clicking the create button
      createNewCollection.mockImplementation(() => Promise.resolve({
        headers: {
          location: "/bookshelf/collections/1",
        }
      }));
      const wrapper = await createComponentWrapper();
      wrapper.findComponent({ref: "createCollectionBtn"}).trigger(
        "click");
      await wrapper.vm.$nextTick();

      wrapper.findComponent("input").setValue("test collection");
      wrapper.findComponent({ref: "saveCollectionBtn"}).trigger(
        "click");

      await flushPromises();

      expect(wrapper.emitted("collection-created")).toBeTruthy();
      expect(wrapper.emitted("collection-created")[0][0]).toStrictEqual(
        "/bookshelf/collections/1");
    });

    it("restState", async () => {
      //Mimics the user entering a collection name and clicking the cancel button on form
      const wrapper = await createComponentWrapper();
      wrapper.findComponent({ref: "createCollectionBtn"}).trigger(
        "click");
      await wrapper.vm.$nextTick();

      wrapper.findComponent("input").setValue("test collection");
      wrapper.findComponent({ref: "closeBtn"}).trigger("click");

      await flushPromises();

      expect(wrapper.vm.$data.dialog).toEqual(false);
      expect(wrapper.vm.$data.collectionName).toEqual(null);
      expect(wrapper.vm.$data.collectionUpdateInProgress).toEqual(false);
    });
  });
});
