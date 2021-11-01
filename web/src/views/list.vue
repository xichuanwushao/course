<template>
  <main role="main">
      <div class="header-nav">
          <div class="clearfix">
              <div class="container">
                  <div class="row">
                      <div class="col-12">
                          <a v-on:click="onClickLevel1('0')" id="category-0" href="javascript:;" class="cur">全部</a>
                          <a v-for="o in level1" v-on:click="onClickLevel1(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>
                      </div>
                  </div>
              </div>
          </div>
      </div>
      <div class="skill clearfix">
          <div class="container">
              <div class="row">
                  <div class="col-12">
                      <a v-on:click="onClickLevel2('1')" id="category-1" href="javascript:;" class="on">不限</a>
                      <a v-for="o in level2" v-on:click="onClickLevel2(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>

                      <div style="clear:both"></div>
                  </div>
              </div>
          </div>
      </div>
    <div class="album py-5 bg-light">
      <div class="container">
          <div class="row">
              <div class="col-md-12">
                  <pagination ref="pagination" v-bind:list="listCourse"></pagination>
              </div>
          </div>
          <br/>
          <div class="row">
              <div v-for="course in courses" class="col-md-4">
                <web-courselist-templates v-bind:course="course"></web-courselist-templates>
              </div>
              <h3 v-show="courses.length == 0">课程还未上架</h3>
          </div>
      </div>
    </div>

  </main>
</template>

<script>
  import WebCourselistTemplates from '../components/web-courselist-templates.vue';
  import Pagination from "../components/pagination";
  export default {
    name: 'list',
    components: {WebCourselistTemplates,Pagination},
    data: function () {
      return {
        courses: [],
          level1:[],
          level2:[],
      }
    },
    mounted() {
      let _this = this;
        _this.$refs.pagination.size = 6;//每页显示多少条数据
        _this.listCourse(1);
        _this.allCategorys();
    },
    methods: {
        /**
         * 查询课程列表
         */
        listCourse(page) {
            let _this = this;
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/course/list', {
                page: page,
                size: _this.$refs.pagination.size,
            }).then((response) => {
                let resp = response.data;
                if (resp.success) {
                    _this.courses = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                }
            }).catch((response) => {
                console.log("error：", response);
            })
        },
        /**
         * 所有分类查询
         */
        allCategorys(){
            let _this = this;
            // Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/web/category/all",{
            }).then((response=>{
                // Loading.hide();
                let resp = response.data;
                _this.categorys = resp.content;
                // 将所有记录格式化成树形结构
                _this.level1 = [];//格式化之前先将level1清空
                for (let i = 0; i < _this.categorys.length; i++) {
                    let c = _this.categorys[i];
                    if (c.parent === '0') {
                        _this.level1.push(c);
                        for (let j = 0; j < _this.categorys.length; j++) {
                            let child = _this.categorys[j];
                            if (child.parent === c.id) {
                                if (Tool.isEmpty(c.children)) {
                                    c.children = [];
                                }
                                c.children.push(child);
                            }
                        }
                    }else{
                        _this.level2.push(c);
                    }
                }
            }))
        },
        /**
         * 点击一级分类时
         * @param level1Id
         */
        onClickLevel1(level1Id) {
            let _this = this;
        },

        /**
         * 点击二级分类时
         * @param level1Id
         */
        onClickLevel2(level2Id) {
            let _this = this;
        },

    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    /* 头部 一级分类 */
    .header-nav {
        height: auto;
        background: #fff;
        box-shadow: 0 8px 16px 0 rgba(28,31,33,.1);
        padding: 16px 0;
        box-sizing: border-box;
        position: relative;
        z-index: 1;
        /*background-color: #d6e9c6;*/
    }
    .header-nav>div {
        width: 100%;
        padding-left: 12px;
        box-sizing: border-box;
        margin-left: auto;
        margin-right: auto;
        /*background-color: #B4D5AC;*/
    }
    .header-nav a {
        float: left;
        font-size: 16px;
        color: #07111b;
        line-height: 50px;
        height: 45px;
        position: relative;
        margin-right: 46px;
        font-weight: 700;
    }
    .header-nav a:hover {
        color: #c80;
    }
    .header-nav a.cur {
        color: #c80;
    }
    .header-nav a.cur:before {
        display: block;
    }
    .header-nav a:before {
        display: none;
        content: ' ';
        position: absolute;
        bottom: 0;
        background: #c80;
        width: 16px;
        height: 3px;
        left: 50%;
        margin-left: -8px;
    }
    /* 二级分类 */
    .skill {
        width: 100%;
        padding: 24px 0 0;
        position: relative;
        margin: 0 auto;
    }
    .skill a.on {
        color: #c80;
        background: rgba(204,136,0,.1);
    }
    .skill a {
        float: left;
        margin-right: 20px;
        padding: 0 12px;
        font-size: 14px;
        color: #4d555d;
        line-height: 32px;
        border-radius: 6px;
        margin-bottom: 12px;
    }
    .skill a:hover {
        background: #d9dde1;
    }
</style>
