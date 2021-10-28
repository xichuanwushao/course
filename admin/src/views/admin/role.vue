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
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
            <th>id</th>
            <th>角色</th>
            <th>描述</th>
            <th>操作</th>
            <tr/>
        </thead>

        <tbody>
        <tr v-for="role in roles">
            <td>{{role.id}}</td>
            <td>{{role.name}}</td>
            <td>{{role.desc}}</td>
        <td>
            <div class="hidden-sm hidden-xs btn-group">
                <button v-on:click="editUser(role)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-user bigger-120"></i>
                </button>

                <button v-on:click="editResource(role)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-list bigger-120"></i>
                </button>
                <button v-on:click="edit(role)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button v-on:click="del(role.id)" class="btn btn-xs btn-danger">
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
                                            <label class="col-sm-2 control-label">角色</label>
                                            <div class="col-sm-10">
                                                <input v-model="role.name" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">描述</label>
                                            <div class="col-sm-10">
                                                <input v-model="role.desc" class="form-control">
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

        <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">角色资源关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                        <button  v-on:click="saveResource" type="button" class="btn btn-primary" >
                            保存
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">角色用户关联配置</h4>
                  </div>
                  <div class="modal-body">
                      <div class="row">
                          <div class="col-md-6">
                              <table id="user-table" class="table table-hover">
                                  <tbody>
                                  <tr v-for="user in users">
                                      <td>{{user.loginName}}</td>
                                      <td class="text-right">
                                          <a  v-on:click = "addUser(user)" href="javascript:;" class="">
                                              <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                                          </a>
                                      </td>
                                  </tr>
                                  </tbody>
                              </table>
                          </div>
                          <div class="col-md-6">
                              <table id="role-user-table" class="table table-hover">
                                  <tbody>
                                  <tr v-for="user in roleUsers">
                                      <td>{{user.loginName}}</td>
                                      <td class="text-right">
                                          <a  v-on:click = "deleteUser(user)" href="javascript:;" class="">
                                              <i class="ace-icon fa fa-trash blue"></i>
                                          </a>
                                      </td>
                                  </tr>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                          <i class="ace-icon fa fa-times"></i>
                          关闭
                      </button>
                      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveUser()">
                          <i class="ace-icon fa fa-plus blue"></i>
                          保存
                      </button>
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
        name: "role",
        data:function (){
            return {
                role: {},
                roles: [],
                resources: [],
                zTree: {},
                users: [],
                roleUsers: []
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("system-role-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.role={};
                $("#form-modal").modal("show")
            },

            edit(role){
                let _this = this;
                _this.role = $.extend({},role);//对象复制
                $("#form-modal").modal("show")
            },
            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                    _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/role/listPage",{
                        page:page,
                        size:_this.$refs.pagination.size,
                    }).then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.roles = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }))
            },
            save(){
                let _this = this;
                // 保存校验 TODO
                if (1 != 1
                    || !Validator.require(_this.role.name, "角色")
                    || !Validator.length(_this.role.name, "角色", 1, 50)
                    || !Validator.require(_this.role.desc, "描述")
                    || !Validator.length(_this.role.desc, "描述", 1, 100)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/system/role/save", _this.role).then((response=>{
                    Loading.hide();
                    // console.log("保存章列表结果：",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        toast.success("保存成功")
                    }else{
                        toast.error(resp.message)
                    }
                }))
            },
            del(id){
                let _this = this;
                Confirm.show("删除章节不可恢复确认删除?",function (){
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/system/role/delete/"+id, _this.role).then((response=>{
                        Loading.hide();
                        // console.log("删除角色列表结果：",response);
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
                //         _this.$ajax.delete("http://127.0.0.1:9000/system/role/delete/"+id, _this.role).then((response=>{
                //             Loading.hide();
                //             console.log("删除角色列表结果：",response);
                //             let resp = response.data;
                //             if (resp.success){
                //                 _this.list( _this.currentPage);
                //                 toast.success("删除成功")
                //             }
                //         }))
                //     }
                // });

            }
            ,
            /**
             * 点击【编辑】
             */
            editResource(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.loadResource();
                $("#resource-modal").modal("show");
            },

            /**
             * 加载资源树
             */
            loadResource() {
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/resource/load-tree').then((res)=>{
                    Loading.hide();
                    let response = res.data;
                    _this.resources = response.content;
                    // 初始化树
                    _this.initTree();
                    _this.listRoleResource();
                })
            },
            /**
             * 初始资源树
             */
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                            enable: true
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
                _this.zTree.expandAll(true);
            },
            /**
             * 资源模态框点击【保存】
             */
            saveResource() {
                let _this = this;
                let resources = _this.zTree.getCheckedNodes();
                console.log("勾选的资源：", resources);

                // 保存时，只需要保存资源id，所以使用id数组进行参数传递
                let resourceIds = [];
                for (let i = 0; i < resources.length; i++) {
                    resourceIds.push(resources[i].id);
                }

                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/role/save-resource', {
                    id: _this.role.id,
                    resourceIds: resourceIds
                }).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        $("#resource-modal").modal("hide");
                        toast.success("保存成功!");
                    } else {
                        toast.warning(resp.message);
                    }
                });
            },
            /**
             * 加载角色资源关联记录
             */
            listRoleResource() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/role/list-resource/' + _this.role.id).then((response)=>{
                    let resp = response.data;
                    let resources = resp.content;

                    // 勾选查询到的资源：先把树的所有节点清空勾选，再勾选查询到的节点
                    _this.zTree.checkAllNodes(false);
                    for (let i = 0; i < resources.length; i++) {
                        let node = _this.zTree.getNodeByParam("id", resources[i]);
                        _this.zTree.checkNode(node, true);
                    }
                });
            },
            /**
             * 点击【用户】
             */
            editUser(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.listUser();
                $("#user-modal").modal("show");
            },

            /**
             * 查询所有用户
             */
            listUser() {
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/user/listPage', {
                    page: 1,
                    size: 9999
                }).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        _this.users = resp.content.list;
                        _this.listRoleUser()
                    } else {
                        toast.warning(resp.message);
                    }
                })
            },

            /**
             * 角色中增加用户
             */
            addUser(user) {
                let _this = this;

                // 如果当前要添加的用户在右边列表中已经有了，则不用再添加
                let users = _this.roleUsers;
                for (let i = 0; i < users.length; i++) {
                    if (user === users[i]) {
                        return;
                    }
                }

                _this.roleUsers.push(user);
            },

            /**
             * 角色中删除用户
             */
            deleteUser(user) {
                let _this = this;
                Tool.removeObj(_this.roleUsers, user);
            },
            /**
             * 角色用户模态框点击【保存】
             */
            saveUser() {
                let _this = this;
                let users = _this.roleUsers;

                // 保存时，只需要保存用户id，所以使用id数组进行参数传递
                let userIds = [];
                for (let i = 0; i < users.length; i++) {
                    userIds.push(users[i].id);
                }
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/role/save-user', {
                    id: _this.role.id,
                    userIds: userIds
                }).then((response)=>{
                    console.log("保存角色用户结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        toast.success("保存成功!");
                    } else {
                        toast.warning(resp.message);
                    }
                })
            },
            /**
             * 加载角色用户
             */
            listRoleUser() {
                let _this = this;
                _this.roleUsers = [];
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/role/list-user/' + _this.role.id).then((res)=>{
                    let response = res.data;
                    let userIds = response.content;

                    // 根据加载到用户ID，到【所有用户数组：users】中查找用户对象，用于列表显示
                    for (let i = 0; i < userIds.length; i++) {
                        for (let j = 0; j < _this.users.length; j++) {
                            if (userIds[i] === _this.users[j].id) {
                                _this.roleUsers.push(_this.users[j]);
                            }
                        }
                    }
                });
            },

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
