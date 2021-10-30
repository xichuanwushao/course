import Vue from 'vue'
import App from './app.vue'
import AppBlog from './App-Blog.vue'
import AppCarousel from './App-Carousel.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
