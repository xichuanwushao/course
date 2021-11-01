<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <router-link class="navbar-brand" to="/">
            <i class="ace-icon fa fa-video-camera"></i>&nbsp;&nbsp;&nbsp;西川课程
        </router-link>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
<!--              <a class="nav-link" href="#">主页 <span class="sr-only">(current)</span></a>-->
              <router-link class="nav-link" to="/">主页 <span class="sr-only">(current)</span></router-link>
            </li>
            <li class="nav-item active">
<!--              <a class="nav-link" href="#">全部课程</a>-->
              <router-link class="nav-link" to="/list">全部课程</router-link>
            </li>
            <li class="nav-item dropdown active">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                更多
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">关于我们</a>
                <a class="dropdown-item" href="#">渠道合作</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">更多信息</a>
              </div>
            </li>
          </ul>
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="搜索课程" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">登录/注册</button>
          </form>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'theHeader',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
