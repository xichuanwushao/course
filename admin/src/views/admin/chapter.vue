<template xmlns:v-on="http://www.w3.org/1999/xhtml">



    <div>
        <button v-on:click="list()" id="loading-btn" type="button" class="btn btn-success" data-loading-text="Loading..."><i class="ace-icon fa fa-refresh "></i><font class="loading-font">刷新</font></button>
<!--        <button class="btn btn-white btn-default btn-round">-->
<!--            <i class="ace-icon fa fa-refresh red2"></i>-->
<!--            刷新-->
<!--        </button>-->
        <!-- PAGE CONTENT BEGINS -->
        <table id="simple-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>章节编号</th>
                        <th>名称</th>
                        <th>课程编号</th>
                        <th>操作</th>


                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="chapter in chapters">


                        <td>{{chapter.id}}</td>
                        <td>{{chapter.name}}</td>
                        <td>{{chapter.courseId}}</td>
                        <td>{{chapter.id}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button class="btn btn-xs btn-success">
                                    <i class="ace-icon fa fa-check bigger-120"></i>
                                </button>

                                <button class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>

                                <button class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>

                                <button class="btn btn-xs btn-warning">
                                    <i class="ace-icon fa fa-flag bigger-120"></i>
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
    </div>
</template>
<script>
    export default {
        name: "chapter",
        data:function (){
            return{
                chapters:[]
            }
        },
        mounted:function () {
            // this.$parent.activeSidebar("business-chapter-sidebar");
            let _this = this;
            _this.list();
        },
        methods:{
            list(){
                let _this = this;
                _this.$ajax.post("http://127.0.0.1:9000/business/chapter/listPage",{
                    page:1,
                    size:1
                }).then((response=>{
                    console.log("查询章列表结果：",response);
                    _this.chapters = response.data.list;
                }))
            }
        }

    }
</script>
<style>
    .btn{
        margin-bottom: 6px;
    }
    #loading-btn{
        width: 120px;
        height: 20px;
        margin-bottom: 6px;
        font-size: 14px;
        font-margin-bottom: 6px;
        padding-bottom: 25px;
    }
    .loading-font{
        /*padding-bottom: 12px;*/
    }
</style>
