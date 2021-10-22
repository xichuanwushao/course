<template>
    <ossali-big-file
            v-bind:input-id="inputId"
            v-bind:text="text"
            v-bind:suffixs="suffixs"
            v-bind:use="use"
            v-bind:after-upload="afterUpload"
            v-bind:shard-size="shardSize"
            v-bind:url="'oss-append'">
    </ossali-big-file>
</template>

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
            shardSize: {
                default:  1 * 1024 * 1024,
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
