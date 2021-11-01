<template>
  <main role="main">

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
        _this.allCategory();
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
        all(){
            let _this = this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/web/category/all",{
            }).then((response=>{
                Loading.hide();
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
                    }
                }
                _this.level2 = [];
                // 对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
                // 注意：界面的渲染需要等vue绑定好变量后才做，所以加延时100ms
            }))
        },
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .title1{
    margin-bottom: 2rem;
    color: #fafafa;
    letter-spacing: 0;
    text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
    font-size: 2rem;
  }
  .title2{
    margin-bottom: 2rem;
    color: transparent;
    -webkit-text-stroke: 1px black;
    letter-spacing: 0.04em;
    font-size: 2rem;
  }

</style>
