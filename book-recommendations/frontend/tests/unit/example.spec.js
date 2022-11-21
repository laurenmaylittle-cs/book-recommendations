import {mount} from '@vue/test-utils'
import HomeView from '@/views/HomeView.vue'
import {getTestData} from '@/api/test'

jest.mock("@/api/test");

function createComponentWrapper({...props} = {}) {
  return mount(HomeView, {
    propsData: {
      ...props,
    }
  });
}

describe('HomeView.vue', () => {
  it('Renders the HomeView', async () => {
    getTestData.mockImplementation(() => Promise.resolve("test data"));

    const wrapper = await createComponentWrapper();
    expect(wrapper.vm.testData).toStrictEqual("test data");
  })
})
