import Vue from 'vue'
import App from './App.vue'
import AppBlog from './App-Blog.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(AppBlog),
}).$mount('#app')
