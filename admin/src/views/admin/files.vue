<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
     <p>
        <!--        <button v-on:click="list(1)" id="Loading-btn" type="button" class="btn btn-success" data-Loading-text="Loading..."><i class="ace-icon fa fa-refresh "></i><font class="Loading-font">刷新</font></button>-->
<!--        <button v-on:click="add()" class="btn btn-white btn-default btn-round">-->
<!--            <i class="ace-icon fa fa-edit "></i>-->
<!--            新增-->
<!--        </button>-->
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
            <th>id</th>
            <th>相对路径</th>
            <th>文件名</th>
            <th>后缀</th>
            <th>大小</th>
            <th>用途</th>
            <th>创建时间</th>
            <th>修改时间</th>
<!--            <th>操作</th>-->
            <tr/>
        </thead>

        <tbody>
        <tr v-for="file in files">
            <td>{{file.id}}</td>
            <td>{{file.path}}</td>
            <td>{{file.name}}</td>
            <td>{{file.suffix}}</td>
            <td>{{file.size | formatFileSize}}</td>
            <td>{{FILE_USE | optionKV(file.use)}}</td>
            <td>{{file.createAt}}</td>
            <td>{{file.updateAt}}</td>
<!--        <td>-->
<!--            <div class="hidden-sm hidden-xs btn-group">-->

<!--                <button v-on:click="edit(file)" class="btn btn-xs btn-info">-->
<!--                    <i class="ace-icon fa fa-pencil bigger-120"></i>-->
<!--                </button>-->

<!--                <button v-on:click="del(file.id)" class="btn btn-xs btn-danger">-->
<!--                    <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--                </button>-->

<!--            </div>-->
<!--        </td>-->
        </tr>
        </tbody>
        </table>
        <!-- PAGE CONTENT ENDS -->

<!--        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">-->
<!--            <div class="modal-dialog" role="document">-->
<!--                <div class="modal-content">-->
<!--                    <div class="modal-header">-->
<!--                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
<!--                        <h4 class="modal-title">表单</h4>-->
<!--                    </div>-->
<!--                    <div class="modal-body">-->
<!--                        <form class="form-horizontal">-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">相对路径</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.path" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">文件名</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.name" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">后缀</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.suffix" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">大小</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.size" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">用途</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <select v-model="file.use" class="form-control">-->
<!--                                                    <option v-for="o in FILE_USE" v-bind:value="o.key">{{o.value}}</option>-->
<!--                                                </select>-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">创建时间</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.createAt" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <div class="form-group">-->
<!--                                            <label class="col-sm-2 control-label">修改时间</label>-->
<!--                                            <div class="col-sm-10">-->
<!--                                                <input v-model="file.updateAt" class="form-control">-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                        </form>-->
<!--                    </div>-->
<!--                    <div class="modal-footer">-->
<!--                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>-->
<!--                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>-->
<!--                    </div>-->
<!--                </div>&lt;!&ndash; /.modal-content &ndash;&gt;-->
<!--            </div>&lt;!&ndash; /.modal-dialog &ndash;&gt;-->
<!--        </div>&lt;!&ndash; /.modal &ndash;&gt;-->
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";
    import Swal from 'sweetalert2'
    export default {
        components: {Pagination},
        name: "file",
        data:function (){
            return {
                file: {},
                files: [],
                FILE_USE: FILE_USE,
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("file-file-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.file={};
                $("#form-modal").modal("show")
            },

            edit(file){
                let _this = this;
                _this.file = $.extend({},files);//对象复制
                $("#form-modal").modal("show")
            },
            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                    _this.$ajax.post(process.env.VUE_APP_SERVER+"/file/file/listPage",{
                        page:page,
                        size:_this.$refs.pagination.size,
                    }).then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.files = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }))
            },
            save(){
                let _this = this;
                // 保存校验 TODO
                if (1 != 1
                    || !Validator.require(_this.file.path, "相对路径")
                    || !Validator.length(_this.file.path, "相对路径", 1, 100)
                    || !Validator.length(_this.file.name, "文件名", 1, 100)
                    || !Validator.length(_this.file.suffix, "后缀", 1, 10)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/file/file/save", _this.file).then((response=>{
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/file/file/delete/"+id, _this.file).then((response=>{
                        Loading.hide();
                        // console.log("删除文件列表结果：",response);
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
                //         _this.$ajax.delete("http://127.0.0.1:9000/file/file/delete/"+id, _this.file).then((response=>{
                //             Loading.hide();
                //             console.log("删除文件列表结果：",response);
                //             let resp = response.data;
                //             if (resp.success){
                //                 _this.list( _this.currentPage);
                //                 toast.success("删除成功")
                //             }
                //         }))
                //     }
                // });

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
