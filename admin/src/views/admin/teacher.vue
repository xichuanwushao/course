<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
     <p>
        <!--        <button v-on:click="list(1)" id="Loading-btn" type="button" class="btn btn-success" data-Loading-text="Loading..."><i class="ace-icon fa fa-refresh "></i><font class="Loading-font">刷新</font></button>-->
        <button v-on:click="add()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit "></i>
            新增
        </button>
        <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh "></i>
            刷新
        </button>
     </p>
        <!-- PAGE CONTENT BEGINS -->
        <pagination  ref="pagination" v-bind:list="list" v-bind:itemCount="3"></pagination>
      <div class="row">
          <div v-for="teacher in teachers" class="col-xs-12 col-sm-3 center">
              <div>
                <span class="profile-picture">
                    <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/teacher/profile-pic.jpg" />
                    <img  v-show="teacher.image" class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" />
                </span>
                  <div class="space-4"></div>
                  <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                      <div class="inline position-relative">
                          <a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                              <i class="ace-icon fa fa-circle light-green"></i>&nbsp;
                              <span class="white">{{teacher.position}}</span>
                          </a>
                      </div>
                  </div>
              </div>
              <div class="space-6"></div>
              <div class="text-center">
                  <div class="text-center">
                      <a href="#" class="btn btn-link">
                          <i class="ace-icon fa fa-user"></i>
                          {{teacher.name}}【{{teacher.nickname}}】
                      </a>
                  </div>

                  <div class="space-6"></div>

                  <div class="profile-social-links align-center">
                      <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                          <i class="ace-icon fa fa-pencil bigger-120"></i>
                      </button>
                      <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                          <i class="ace-icon fa fa-trash-o bigger-120"></i>
                      </button>
                  </div>
              </div>

              <div class="hr hr12 dotted"></div>

          </div>
      </div>

      <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
            <th>id</th>
            <th>姓名</th>
            <th>昵称</th>
            <th>头像</th>
            <th>职位</th>
            <th>座右铭</th>
            <th>简介</th>
            <th>操作</th>
            <tr/>
        </thead>

        <tbody>
        <tr v-for="teacher in teachers">
            <td>{{teacher.id}}</td>
            <td>{{teacher.name}}</td>
            <td>{{teacher.nickname}}</td>
            <td>{{teacher.image}}</td>
            <td>{{teacher.position}}</td>
            <td>{{teacher.motto}}</td>
            <td>{{teacher.intro}}</td>
        <td>
            <div class="hidden-sm hidden-xs btn-group">

                <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>

            </div>

            <div class="hidden-md hidden-lg">
                <div class="inline pos-rel">
                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                        <li>
                            <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                                                    <span class="blue">
                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                    </span>
                            </a>
                        </li>

                        <li>
                            <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                            </a>
                        </li>

                        <li>
                            <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                    <span class="red">
                                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                    </span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </td>
        </tr>
        </tbody>
        </table>
        <!-- PAGE CONTENT ENDS -->

        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">昵称</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.nickname" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">头像</label>
                                <div class="col-sm-10">
                                    <file v-bind:text="'上传头像'"
                                    v-bind:after-upload="afterUpload"
                                    v-bind:input-id="'image-upload'"
                                    v-bind:use="FILE_USE.TEACHER.key"
                                    v-bind:suffixs="['jpg','jpeg','png']" ></file>
                                    <div v-show="teacher.image" class="row">
                                        <div class="col-md-4">
                                            <img v-bind:src="teacher.image" class="img-responsive" >
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">职位</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.position" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">座右铭</label>
                                <div class="col-sm-10">
                                    <input v-model="teacher.motto" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">简介</label>
                                <div class="col-sm-10">
<!--                                            <input v-model="teacher.intro" class="form-control">-->
                                    <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";
    import File from "../../components/file";
    import Swal from 'sweetalert2'
    export default {
        components: {Pagination,File},
        name: "teacher",
        data:function (){
            return {
                teacher: {},
                teachers: [],
                FILE_USE: FILE_USE,
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("business-teacher-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.teacher={};
                $("#form-modal").modal("show")
            },

            edit(teacher){
                let _this = this;
                _this.teacher = $.extend({},teacher);//对象复制
                $("#form-modal").modal("show")
            },
            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                    _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/teacher/listPage",{
                        page:page,
                        size:_this.$refs.pagination.size,
                    }).then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.teachers = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }))
            },
            save(){
                let _this = this;
                // 保存校验 TODO
                if (1 != 1
                    || !Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, 50)
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
                    || !Validator.length(_this.teacher.image, "头像", 1, 100)
                    || !Validator.length(_this.teacher.position, "职位", 1, 50)
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
                    || !Validator.length(_this.teacher.intro, "简介", 1, 500)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/teacher/save", _this.teacher).then((response=>{
                    Loading.hide();
                    // console.log("保存章列表结果：",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        toast.success("保存成功")
                    }else{
                        toast.success(resp.message)
                    }
                }))
            },
            del(id){
                let _this = this;
                Confirm.show("删除章节不可恢复确认删除?",function (){
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/business/teacher/delete/"+id, _this.teacher).then((response=>{
                        Loading.hide();
                        // console.log("删除讲师列表结果：",response);
                        let resp = response.data;
                        if (resp.success){
                            _this.list( _this.currentPage);
                            toast.success("删除成功")
                        }
                    }))
                })
                // Swal.fire({
                //     title: '确认删除?',
                //     text: "删除不可恢复确认删除?",
                //     icon: 'warning',
                //     showCancelButton: true,
                //     confirmButtonColor: '#3085d6',
                //     cancelButtonColor: '#d33',
                //     confirmButtonText: '确认!'
                // }).then((result) => {
                //     if (result.isConfirmed) {
                //         Loading.show();
                //         _this.$ajax.delete("http://127.0.0.1:9000/business/teacher/delete/"+id, _this.teacher).then((response=>{
                //             Loading.hide();
                //             console.log("删除讲师列表结果：",response);
                //             let resp = response.data;
                //             if (resp.success){
                //                 _this.list( _this.currentPage);
                //                 toast.success("删除成功")
                //             }
                //         }))
                //     }
                // });

            },afterUpload(resp){
                let _this = this;
                let image = resp.content.path;
                _this.teacher.image = image;
            }

        }

    }
</script>
<style>
    .btn{
        margin-top: 0;
        margin-right: 5px;
    }
    #Loading-btn{
        width: 120px;
        height: 20px;
        margin-bottom: 6px;
        font-size: 14px;
        font-margin-bottom: 6px;
        padding-bottom: 25px;
    }
    .Loading-font{
        /*padding-bottom: 12px;*/
    }
</style>
