<template>
    <div class="card mb-4 shadow-sm course">
        <img class="img-fluid" v-bind:src="course.image">
        <div class="card-body">
            <h4 class="">{{course.name}}</h4>
            <p   id="Loading-btn" >{{course.summary}}</p>
            <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                </div>
                <div class="text-muted">
                    <span class="badge badge-info"><i class="fa fa-yen" aria-hidden="true"></i>&nbsp;{{course.price}}</span>&nbsp;
                    <span class="badge badge-info"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;{{course.enroll}}</span>&nbsp;
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'web-courselist-template',
        props:{
            course:{}
        },

        data: function () {
            return {
            }
        },
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .course h4 {
        font-size: 1.25rem;
        margin: 15px 0;
    }

    .course .text-muted .badge {
        font-size: 1rem;
    }

    #Loading-btn{
        font-size: 14px;
        font-weight: 400;
        font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
        font-margin-bottom: 6px;
    }
</style>
