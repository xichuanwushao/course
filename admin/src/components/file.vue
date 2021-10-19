<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload "></i>
            {{text}}
        </button>
        <input class="hidden" type="file" ref="gaga" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
    </div>
</template>

<script>
  export default {
    name: 'file',
    props: {//可配置的属性
        text:{
            default: "上传文件"
        },
        inputId:{
            default: "file-upload"
        },
        suffixs:{
            default: []
        },
        use:{
            default: ""
        },
        afterUpload: {
        type: Function,
        default: null
      },
      itemCount: Number // 显示的页码数，比如总共有100页，只显示10页，其它用省略号表示
    },
    data: function () {
      return {

      }
    },
    methods: {
        uploadFile(){
            let _this = this;
            let formData = new window.FormData();

            let file = _this.$refs.gaga.files[0];

            // 判断文件格式
            let suffixs = _this.suffixs;
            let fileName = file.name;
            let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
            let validateSuffix = false;
            for (let i = 0; i < suffixs.length; i++) {
                if (suffixs[i].toLowerCase() === suffix) {
                    validateSuffix = true;
                    break;
                }
            }
            if (!validateSuffix) {
                toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
                $("#" + _this.inputId + "-input").val("");//修复连续选择第二个文件的时候 第二个文件没有反应
                return;
            }

            // key : "file"必须和后端controller参数同名
            // formData.append('file',document.querySelector("#" + _this.inputId + "-input").files[0]);
            formData.append('file',file);
            formData.append('use',_this.use);
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/upload',formData).then((response)=>{
                Loading.hide();
                let resp = response.data;
                console.log("上传文件成功: ",resp);
                // let image = resp.content;

                _this.afterUpload(resp);
                $("#" + _this.inputId + "-input").val("");//修复连续选择第二个文件的时候 第二个文件没有反应
                // console.info("头像地址:",image);
                // _this.teacher.image = image;
            });
        },
        selectFile(){
            let _this = this;
            $("#" + _this.inputId + "-input").trigger("click");//不应该写固定
        }
    }
  }
</script>
