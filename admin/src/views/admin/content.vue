<template>
    <div>
        <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog" >
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content"   >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">内容编辑</h4>
                    </div>
                    <div class="modal-body">
                        <file v-bind:input-id="'content-file-upload'"
                              v-bind:text="'上传文件'"
                              v-bind:suffixs="['jpg','jpeg','png','webp','mp4','avi']"
                              v-bind:use="FILE_USE.COURSE.key"
                              v-bind:after-upload="afterUploadContentFile"
                        ></file>
                        <br>
                        <table id="file-table" class="table  table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>地址</th>
                                <th>大小</th>
                                <th>操作</th>
                            <tr/>
                            </thead>

                            <tbody>
                            <tr v-for="(f,i) in files" v:bind:key="f.id">
                                <td>{{f.name}}</td>
                                <td>{{f.url}}</td>
                                <td>{{f.size | formatFileSize }}</td>
                                <td>
                                    <button v-on:click="delFile(f)" class="btn btn-white btn-xs btn-warning btn-round">
                                        <i class="ace-icon fa fa-times red2"></i>
                                        删除
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
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
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";
    import File from "../../components/file";
    import Swal from 'sweetalert2'
    export default {
        components: {Pagination,File},
        name: "course",
        data:function (){
            return {
                course: {},
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
                FILE_USE:FILE_USE,
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
                files:[],
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

        //加载内容文件列表
        //_this.listContentFiles();

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
                toast.warning(resp.message);
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
    },afterUpload(resp){
        let _this = this;
        let image = resp.content.path;
        _this.course.image = image;
    } , /**
     * 加载内容文件列表
     */
    listContentFiles() {
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/course-content-file/list/' + _this.course.id).then((response)=>{
            let resp = response.data;
            if (resp.success) {
                _this.files = resp.content;
            }
        });
    },  /**
     * 上传内容文件后，保存内容文件记录
     */
    afterUploadContentFile(response) {
        let _this = this;
        console.log("开始保存文件记录");
        let file = response.content;
        file.courseId = _this.course.id;
        file.url = file.path;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/course-content-file/save', file).then((response)=>{
            let resp = response.data;
            if (resp.success) {
                toast.success("上传文件成功");
                _this.files.push(resp.content);
            }
        });

    },    /**
     * 删除内容文件
     */
    delFile(f) {
        let _this = this;
        Confirm.show("删除课程后不可恢复，确认删除？", function () {
            _this.$ajax.delete(process.env.VUE_APP_SERVER + '/file//course-content-file/delete/' + f.id).then((response)=>{
                let resp = response.data;
                if (resp.success) {
                    toast.success("删除文件成功");
                    Tool.removeObj(_this.files, f);
                }
            });
        });
    },
    }

    }
</script>
<style>

</style>