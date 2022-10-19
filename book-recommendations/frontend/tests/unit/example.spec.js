import { shallowMount } from '@vue/test-utils'
import HomeView from '@/views/HomeView.vue'

describe('HomeView.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'new message'
    const wrapper = shallowMount(HomeView, {
      propsData: { msg }
    })
    expect(wrapper.text()).toMatch(msg)
  })
})
