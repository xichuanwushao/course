<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div>
        <!--        <button v-on:click="list(1)" id="Loading-btn" type="button" class="btn btn-success" data-Loading-text="Loading..."><i class="ace-icon fa fa-refresh "></i><font class="Loading-font">刷新</font></button>-->
        <button v-on:click="add()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit "></i>
            新增
        </button>
        <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh "></i>
            刷新
        </button>
        <!-- PAGE CONTENT BEGINS -->
        <pagination  ref="pagination" v-bind:list="list" v-bind:itemCount="3"></pagination>
        <div class="row">
            <div v-for="course in courses" class="col-md-3">
                <div class="thumbnail search-thumbnail">
                    <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
                    <img  v-show="course.image" class="media-object" v-bind:src="course.image" />
                    <div class="caption">
                        <div class="clearfix">
                            <span class="pull-right label label-primary info-label">{{COURSE_LEVEL | optionKV(course.level)}}</span>
                            <span class="pull-right label label-primary info-label">{{COURSE_CHARGE | optionKV(course.charge)}}</span>
                            <span class="pull-right label label-primary info-label">{{COURSE_STATUS | optionKV(course.status)}}</span>
                        </div>

                        <h3 class="search-title">
                            <a href="#" class="blue">{{course.name}}</a>
                        </h3>

                        <!--对老师进行过滤 过滤的条件是老师的id==课程的id-->
                        <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
                            <div>
                                <img v-show="!teacher.image" class="pull-left" src="/static/image/teacher/profile-pic.jpg">
                                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                                <a class="user" href="#"> {{teacher.name}} </a>
                                <br>
                                {{teacher.position}}
                            </div>
                        </div>


                        <p>
                            <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
                        </p>
                        <p>{{course.summary}}</p>
                        <p>
                            <span class="badge badge-info">{{course.id}}</span>
                            <span class="badge badge-info">排序：{{course.sort}}</span>
                            <span class="badge badge-info">{{course.time   | formatSecond}}</span>
                        </p>
                        <p>
                            <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                                大章
                            </button>&nbsp;
                            <button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                                内容
                            </button>&nbsp;
                            <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                                排序
                            </button>&nbsp;
                            <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                                编辑
                            </button>&nbsp;
                            <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                删除
                            </button>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
            <th>id</th>
            <th>名称</th>
            <th>概述</th>
            <th>时长</th>
            <th>价格(元)</th>
            <th>封面</th>
            <th>级别</th>
            <th>收费</th>
            <th>状态</th>
            <th>报名数</th>
            <th>顺序</th>
            <th>操作</th>
            <tr/>
        </thead>

        <tbody>
        <tr v-for="course in courses">
            <td>{{course.id}}</td>
            <td>{{course.name}}</td>
            <td>{{course.summary}}</td>
            <td>{{course.time}}</td>
            <td>{{course.price}}</td>
            <td>{{course.image}}</td>
            <td>{{COURSE_LEVEL | optionKV(course.level)}}</td>
            <td>{{COURSE_CHARGE | optionKV(course.charge)}}</td>
            <td>{{COURSE_STATUS | optionKV(course.status)}}</td>
            <td>{{course.enroll}}</td>
            <td>{{course.sort}}</td>
        <td>
            <div class="hidden-sm hidden-xs btn-group">

                <button v-on:click="edit(course)" class="btn btn-xs btn-info">
                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button v-on:click="del(course.id)" class="btn btn-xs btn-danger">
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
                                <label class="col-sm-2 control-label">分类</label>
                                <div class="col-sm-10">
                                    <ul id="tree" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="course.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">讲师</label>
                                <div class="col-sm-10">
                                    <select v-model="course.teacherId" class="form-control">
                                        <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">概述</label>
                                <div class="col-sm-10">
                                    <input v-model="course.summary" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">时长</label>
                                <div class="col-sm-10">
                                    <input v-model="course.time" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">价格(元)</label>
                                <div class="col-sm-10">
                                    <input v-model="course.price" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">封面</label>
                                <div class="col-sm-10">
                                    <input v-model="course.image" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">级别</label>
                                <div class="col-sm-10">
                                    <select v-model="course.level" class="form-control">
                                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">收费</label>
                                <div class="col-sm-10">
                                    <select v-model="course.charge" class="form-control">
                                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">状态</label>
                                <div class="col-sm-10">
                                    <select v-model="course.status" class="form-control">
                                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">报名数</label>
                                <div class="col-sm-10">
                                    <input v-model="course.enroll" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="course.sort" class="form-control" disabled>
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
        <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">内容编辑</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <div class="col-sm-12">
                                    {{saveContentLabel}}
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div id="content" ></div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button v-on:click="saveContent()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">排序</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">当前排序</label>
                                <div class="col-sm-10">
                                    <input  class="form-control" v-model="sort.oldSort" name="oldSort" disabled >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">新排序</label>
                                <div class="col-sm-10">
                                    <input class="form-control" v-model="sort.newSort" name="newSort" >
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button v-on:click="updateSort()" type="button" class="btn btn-primary">保存</button>
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
        name: "course",
        data:function (){
            return {
                course: {},
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
                categorys:[],
                tree: {},
                saveContentInterval: {},
                saveContentLabel: "",
                sort:{
                    id:"",
                    oldSort:0,
                    newSort:0,
                },
                teachers:[],
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("business-course-sidebar");
            let _this = this;
            _this.$refs.pagination.size = 5;
            _this.allCategory();
            _this.allTeachers();
            _this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.course={
                    sort: _this.$refs.pagination.total + 1
                };
                _this.tree.checkAllNodes(false);//让所有节点都不选中
                $("#form-modal").modal("show");
            },

            edit(course){
                let _this = this;
                _this.course = $.extend({},course);//对象复制
                _this.listCategory(course.id);
                $("#form-modal").modal("show");
            },

            list(page){
                let _this = this;
                Loading.show();
                _this.currentPage=page,
                    _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/course/listPage",{
                        page:page,
                        size:_this.$refs.pagination.size,
                    }).then((response=>{
                        Loading.hide();
                        // console.log("查询章列表结果：",response);
                        let resp = response.data;
                        _this.courses = resp.content.list;
                        _this.$refs.pagination.render(page, resp.content.total);
                    }))
            },
            save(){
                let _this = this;
                // 保存校验 TODO
                if (1 != 1
                    || !Validator.require(_this.course.name, "名称")
                    || !Validator.length(_this.course.name, "名称", 1, 50)
                    || !Validator.length(_this.course.summary, "概述", 1, 2000)
                    || !Validator.length(_this.course.image, "封面", 1, 100)
                    || !Validator.require(_this.course.level, "级别")
                ) {
                    return;
                }

                Loading.show();
                let categorys = _this.tree.getChangeCheckedNodes();
                _this.course.categorys = categorys;
                if(Tool.isEmpty(categorys)){
                    toast.warning("请选择分类！");
                    return;
                }
                console.log(categorys);
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/course/save", _this.course).then((response=>{
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/business/course/delete/"+id, _this.course).then((response=>{
                        Loading.hide();
                        // console.log("删除课程列表结果：",response);
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
                //         _this.$ajax.delete("http://127.0.0.1:9000/business/course/delete/"+id, _this.course).then((response=>{
                //             Loading.hide();
                //             console.log("删除课程列表结果：",response);
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
             * 点击【大章】
             */
            toChapter(course) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_COURSE, course);
                _this.$router.push("/business/chapter");//跳转到大章
            },
            allCategory(){
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/category/all",{
                }).then((response=>{
                    Loading.hide();
                    // console.log("查询章列表结果：",response);
                    let resp = response.data;
                    _this.categorys = resp.content;
                    _this.initTree();
                }))
            },
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "0",
                        }
                    }
                };

                let zNodes = _this.categorys;
                _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
                //初始化的时候展开所以节点
                _this.tree.expandAll(true);
            },
            /**
             * 查找课程下所有分类
             * @param courseId
             */
            listCategory(courseId) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/course/list-category/' + courseId).then((res)=>{
                    Loading.hide();
                    console.log("查找课程下所有分类结果：", res);
                    let response = res.data;
                    let categorys = response.content;

                    // 勾选查询到的分类
                    _this.tree.checkAllNodes(false);
                    for (let i = 0; i < categorys.length; i++) {
                        let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
                        _this.tree.checkNode(node, true);
                    }
                })
            },
            /**
             * 打开内容编辑框
             */
            editContent(course) {
                let _this = this;
                let id = course.id;
                _this.course = course;
                $("#content").summernote({//初始化富文本框
                    focus: true,
                    height: 300
                });
                $("#content").summernote('code', '');  // 先清空历史文本
                _this.saveContentLabel="";
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/courseContent/find-content/' + id).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#course-content-modal").modal({backdrop:'static', keyboard:false});//调用modal方法时 增加backdrop:'static' 点击空白位置 模态框不会自动关闭
                        if (resp.content) {
                            $("#content").summernote('code', resp.content.content);//读取到内容 再将内容显示到文本框
                        }
                        // 定时自动保存
                        _this.saveContentInterval = setInterval(function() {
                            _this.saveContent();
                        }, 5000);
                        // 关闭内容框时 清空自动保存任务
                        $("#course-content-modal").on('hidden.bs.modal', function(e){
                            clearInterval(_this.saveContentInterval);
                        })//调用modal方法时 增加backdrop:'static' 点击空白位置 模态框不会自动关闭

                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },
            /**
             * 保存内容
             */
            saveContent () {
                let _this = this;
                let content = $("#content").summernote("code");//将文本框里面的内容提取出来
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/courseContent/save-content', {
                    id: _this.course.id,
                    content: content
                }).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        // toast.success("内容保存成功");
                        let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
                        _this.saveContentLabel="最后保存时间："+now;
                    } else {
                        toast.warning(resp.message);
                    }
                });
            },
            openSortModal(course) {
                let _this = this;
                _this.sort = {
                    id: course.id,
                    oldSort: course.sort,
                    newSort: course.sort
                };
                $("#course-sort-modal").modal("show");
             },
                /***
                 * 排序
                 */
            updateSort(){
                let _this = this;
                if(_this.sort.newSort === _this.sort.oldSort){
                    toast.warning("排序没有变化");
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/course/sort", _this.sort).then((res)=>{
                    let response = res.data;

                    if(response.success){
                        toast.success("更新排序成功");
                        $("#course-sort-modal").modal("hide");
                        _this.list(1);
                    }else{
                        toast.error("更新排序失败");
                    }
                })
            },
            allTeachers(){
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/teacher/all",{
                }).then((response=>{
                    Loading.hide();
                    // console.log("查询章列表结果：",response);
                    let resp = response.data;
                    console.log("查询allTeachers结果：",response.data);
                    console.log("查询allTeachers结果：",resp.content);
                    _this.teachers = resp.content;
                    console.log("查询allTeachers结果：",_this.teachers);
                }))
            },
        }

    }
</script>
<style scoped>
    .caption h3{
       font-size: 20px;
    }
    @media (max-width: 1199px) {
        .caption h3 {
            font-size: 16px;
        }
    }
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
