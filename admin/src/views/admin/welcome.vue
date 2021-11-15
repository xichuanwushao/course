<template>
    <div>
        <div class="space-12"></div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="text-center">
          <span class="btn btn-app btn-sm btn-light no-hover">
              <span class="line-height-1 bigger-170 blue"> 518 </span>
              <br>
              <span class="line-height-1 smaller-90"> 课程 </span>
          </span>
                        <span class="btn btn-app btn-sm btn-yellow no-hover">
              <span class="line-height-1 bigger-170"> 6,321 </span>

              <br>
              <span class="line-height-1 smaller-90"> 大章 </span>
          </span>

                        <span class="btn btn-app btn-sm btn-pink no-hover">
              <span class="line-height-1 bigger-170"> 4,182 </span>

              <br>
              <span class="line-height-1 smaller-90"> 小节 </span>
          </span>

                        <span class="btn btn-app btn-sm btn-success no-hover">
              <span class="line-height-1 bigger-170"> 5.23 </span>

              <br>
              <span class="line-height-1 smaller-90"> 会员(万) </span>
          </span>

                        <span class="btn btn-app btn-sm btn-primary no-hover">
              <span class="line-height-1 bigger-170"> 12.76 </span>

              <br>
              <span class="line-height-1 smaller-90"> 报名(万) </span>
          </span>

                        <span class="btn btn-app btn-sm btn-grey no-hover">
              <span class="line-height-1 bigger-170"> 32.96 </span>

              <br>
              <span class="line-height-1 smaller-90"> 评论(万) </span>
          </span>

                    </div>

                    <div class="space-12"></div>

                    <div class="hr hr2 hr-double"></div>

                    <div class="space-12"></div>

                </div>
                <div class="col-sm-12 infobox-container">
                    <div class="infobox infobox-green">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-comments"></i>
                        </div>

                        <div class="infobox-data">
                            <span class="infobox-data-number">332</span>
                            <div class="infobox-content">新增评论</div>
                        </div>

                        <div class="stat stat-success">8%</div>
                    </div>

                    <div class="infobox infobox-blue">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-flask"></i>
                        </div>

                        <div class="infobox-data">
                            <span class="infobox-data-number">58</span>
                            <div class="infobox-content">新增注册会员</div>
                        </div>

                        <div class="badge badge-success">
                            +10%
                            <i class="ace-icon fa fa-arrow-up"></i>
                        </div>
                    </div>

                    <div class="infobox infobox-pink">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-shopping-cart"></i>
                        </div>

                        <div class="infobox-data">
                            <span class="infobox-data-number">108</span>
                            <div class="infobox-content">课程报名</div>
                        </div>
                        <div class="stat stat-important">4%</div>
                    </div>

                    <div class="space-12"></div>

                    <div class="hr hr2 hr-double"></div>

                    <div class="space-12"></div>

                </div>
                <div class="col-sm-7">
                    <div class="widget-box transparent">
                        <div class="widget-header widget-header-flat">
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-signal"></i>
                                Sale Stats
                            </h4>

                            <div class="widget-toolbar">
                                <a href="#" data-action="collapse">
                                    <i class="ace-icon fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main padding-4">
                                <div id="sales-charts"></div>
                            </div><!-- /.widget-main -->
                        </div><!-- /.widget-body -->
                    </div><!-- /.widget-box -->
                </div><!-- /.col -->
            </div>
    </div>
</template>
<script>
    export default {
        name: "welcome",
        mounted:function () {
            // this.$parent.activeSidebar("welcome-sidebar");
            let _this = this;
            _this.drawSaleChart();
        },
        methods:{
            drawSaleChart(){
                // 生成随机两组数据
                let d1 = [];
                for (let i = 0; i < 30; i += 1) {
                    d1.push([i + 1, 2000 + Math.floor((Math.random()*100)+1)]);
                }
                let d2 = [];
                for (let i = 0; i < 30; i += 1) {
                    d2.push([i + 1, 1900 + Math.floor((Math.random()*100)+1)]);
                }
                let d3 = [];
                for (let i = 0; i < 30; i += 1) {
                    d3.push([i + 1, 1900 + Math.floor((Math.random()*100)+1)]);
                }
                var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
                $.plot("#sales-charts", [
                    { label: "Domains", data: d1 },
                    { label: "Hosting", data: d2 },
                    { label: "Services", data: d3 }
                ], {
                    hoverable: true,
                    shadowSize: 0,
                    series: {
                        lines: { show: true },
                        points: { show: true }
                    },
                    xaxis: {
                        tickLength: 0
                    },
                    yaxis: {
                        ticks: 10,
                        min: -2,
                        max: 2,
                        tickDecimals: 3
                    },
                    grid: {
                        backgroundColor: { colors: [ "#fff", "#fff" ] },
                        borderWidth: 1,
                        borderColor:'#555'
                    }
                });
            }

        }

    }
</script>
