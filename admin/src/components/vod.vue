<template>
    <ossali-big-file
            v-bind:input-id="inputId"
            v-bind:text="text"
            v-bind:suffixs="suffixs"
            v-bind:use="use"
            v-bind:after-upload="afterUpload"
            v-bind:shard-size="1000 * 1024* 1024"
            v-bind:url="'vod'">
    </ossali-big-file>
</template>
<!--
    由于视频点播不支持追加上传 所以使用vod组件进行上传 只能有一个分片
-->
<script>
    import OssaliBigFile from "./ossali-big-file";
    export default {
        components: {OssaliBigFile},
        name: 'vod',
        props: {//可配置的属性
            text: {
                default: "上传VOD"
            },
            inputId: {
                default: "vod-upload"
            },
            suffixs: {
                default: []
            },
            use: {
                default: ""
            },
            url:{
                default:"oss-append",
            },
            afterUpload: {
                type: Function,
                default: null
            },
        },

    }
</script>
