import AppBlog from './App-Blog.vue'
import AppCarousel from './App-Carousel.vue'
import Vue from 'vue'
import App from './app.vue'
import router from './router'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
