<template xmlns:v-on="http://www.w3.org/1999/xhtml">
        <div>
            <h4 class="lighter">
                <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
            </h4>
            <hr>
            <p>
        <router-link to="/business/course" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-arrow-left "></i>
            返回课程
        </router-link>
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
        <table id="simple-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>章节编号</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="chapter in chapters">


                        <td>{{chapter.id}}</td>
                        <td>{{chapter.name}}</td>
                        <td>{{chapter.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                                    小节
                                </button>&nbsp;
                                <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                                    编辑
                                </button>&nbsp;
                                <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                    删除
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
                                <label  class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.name" type="text" class="form-control"  placeholder="名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">课程</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{course.name}}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.sort" type="text" class="form-control"  placeholder="顺序">
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
    import Swal from 'sweetalert2'
    export default {
        components: {Pagination},
        name: "chapter",
        data:function (){
            return{
                chapter:{},
                chapters:[],
                currentPage:1,
                course:{}
            }
        },
        mounted:function () {
            //sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            let course = SessionStorage.get(SESSION_KEY_COURSE)|| {};
            if(Tool.isEmpty(course)){
                _this.$router.push("/welcome")
            }
            _this.course=course;
            _this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.chapter={};
                $("#form-modal").modal("show")
            },

            edit(chapter){
                let _this = this;
                _this.chapter = $.extend({},chapter);//对象复制
                $("#form-modal").modal("show")
            },
            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/chapter/listPage",{
                    page:page,
                    size:_this.$refs.pagination.size,
                    courseId: _this.course.id
                }).then((response=>{
                    Loading.hide();
                    // console.log("查询章列表结果：",response);
                    let resp = response.data;
                    _this.chapters = resp.content.list;
                    Tool.sortAsc(_this.chapters, "sort");
                    _this.$refs.pagination.render(page, resp.content.total);
                }))
            },
            save(){
                let _this = this;
                // 保存校验
                if (!Validator.require(_this.chapter.name, "名称")
                    || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
                    return;
                }
                _this.chapter.courseId = _this.course.id;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/chapter/save", _this.chapter).then((response=>{
                    Loading.hide();
                    // console.log("保存章列表结果：",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(_this.currentPage);
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/business/chapter/delete/"+id, _this.chapter).then((response=>{
                        Loading.hide();
                        // console.log("删除大章列表结果：",response);
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
                //         _this.$ajax.delete("http://127.0.0.1:9000/business/chapter/delete/"+id, _this.chapter).then((response=>{
                //             Loading.hide();
                //             console.log("删除大章列表结果：",response);
                //             let resp = response.data;
                //             if (resp.success){
                //                 _this.list( _this.currentPage);
                //                 toast.success("删除成功")
                //             }
                //         }))
                //     }
                // });

            },
            /**
             * 点击【小节】
             */
            toSection(chapter) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
                _this.$router.push("/business/section-local");
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
