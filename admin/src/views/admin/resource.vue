<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div>
        <!--        <button v-on:click="list(1)" id="Loading-btn" type="button" class="btn btn-success" data-Loading-text="Loading..."><i class="ace-icon fa fa-refresh "></i><font class="Loading-font">刷新</font></button>-->
        <button v-on:click="list(1);listj()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh "></i>
            刷新
        </button>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
          <i class="ace-icon fa fa-edit "></i>
          新增
      </button>
      <div class="row">
          <div class="col-md-6">
              <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>

              <br>
              <button id="save-btn" type="button" class="btn btn-primary" v-on:click="savej()">
                  保存
              </button>
          </div>
          <div class="col-md-6">

              <ul id="tree" class="ztree"></ul>
          </div>
      </div>



        <!-- PAGE CONTENT BEGINS -->
        <pagination  ref="pagination" v-bind:list="list" v-bind:itemCount="3"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
            <th>id</th>
            <th>名称</th>
            <th>页面</th>
            <th>请求</th>
            <th>父id</th>
            <th>操作</th>
            <tr/>
        </thead>

        <tbody>
        <tr v-for="resource in resources">
            <td>{{resource.id}}</td>
            <td>{{resource.name}}</td>
            <td>{{resource.page}}</td>
            <td>{{resource.request}}</td>
            <td>{{resource.parent}}</td>
        <td>
            <div class="hidden-sm hidden-xs btn-group">

                <button v-on:click="edit(resource)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button v-on:click="del(resource.id)" class="btn btn-xs btn-danger">
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
                                            <label class="col-sm-2 control-label">id</label>
                                            <div class="col-sm-10">
                                                <input v-model="resource.id" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">名称</label>
                                            <div class="col-sm-10">
                                                <input v-model="resource.name" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">页面</label>
                                            <div class="col-sm-10">
                                                <input v-model="resource.page" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">请求</label>
                                            <div class="col-sm-10">
                                                <input v-model="resource.request" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">父id</label>
                                            <div class="col-sm-10">
                                                <input v-model="resource.parent" class="form-control">
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
        name: "resource",
        data:function (){
            return {
                resource: {},
                resources: [],
                resources2: [],
                resourceStr: "",
                tree: {},
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("system-resource-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
            _this.listj();

        },
        methods:{
            add(){
                let _this = this;
                _this.resource={};
                $("#form-modal").modal("show")
            },

            edit(resource){
                let _this = this;
                _this.resource = $.extend({},resource);//对象复制
                $("#form-modal").modal("show")
            },
            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                    _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/resource/listPage",{
                        page:page,
                        size:_this.$refs.pagination.size,
                    }).then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.resources = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }))
            },
            listj(){
                let _this = this;
                Loading.show();
                    _this.$ajax.get(process.env.VUE_APP_SERVER+"/system/resource/load-tree").then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.resources2 = resp.content;
                       _this.initTree();
                    }))
            },
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: false
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                        }
                    }
                };
                _this.tree = $.fn.zTree.init($("#tree"), setting, _this.resources2);
                //初始化的时候展开所以节点
                _this.tree.expandAll(true);
            },
            savej(){
                let _this = this;
                // 保存校验 TODO
                // 保存校验
                if (Tool.isEmpty(_this.resourceStr)) {
                    toast.warning("资源不能为空！");
                    return;
                }
                let json = null;
                try{
                    json = JSON.parse(_this.resourceStr);
                }catch (e) {
                    toast.warning("JSON格式错误！")
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/resource/save-json', json).then((response)=>{
                    Loading.hide();
                    // console.log("保存章列表结果：",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        _this.listj();
                        toast.success("保存成功")
                    }else{
                        toast.warning(resp.message)
                          }
                    })
                },
            save(){
                let _this = this;
                // 保存校验 TODO
                if (1 != 1
                    || !Validator.require(_this.resource.id, "id")
                    || !Validator.require(_this.resource.name, "名称")
                    || !Validator.length(_this.resource.name, "名称", 1, 100)
                    || !Validator.length(_this.resource.page, "页面", 1, 50)
                    || !Validator.length(_this.resource.request, "请求", 1, 200)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/resource/save", _this.resource).then((response=>{
                    Loading.hide();
                    // console.log("保存章列表结果：",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        _this.listj();
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/system/resource/delete/"+id, _this.resource).then((response=>{
                        Loading.hide();
                        // console.log("删除资源列表结果：",response);
                        let resp = response.data;
                        if (resp.success){
                            _this.list( _this.currentPage);
                            _this.listj();
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
                //         _this.$ajax.delete("http://127.0.0.1:9000/system/resource/delete/"+id, _this.resource).then((response=>{
                //             Loading.hide();
                //             console.log("删除资源列表结果：",response);
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
