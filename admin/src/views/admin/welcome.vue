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
                <div class="col-sm-12">
                    <div class="widget-box transparent">
                        <div class="widget-header widget-header-flat">
                            <h4 class="widget-title lighter">
                                <i class="ace-icon fa fa-signal"></i>
                                销售量
                            </h4>

                        </div>

                        <div class="widget-body">
                            <div class="widget-main padding-4">
                                <div id="sales-charts"></div>
                            </div><!-- /.widget-main -->
                        </div><!-- /.widget-body -->
                    </div><!-- /.widget-box -->

                    <div class="space-12"></div>

                    <div class="hr hr2 hr-double"></div>

                    <div class="space-12"></div>
                </div><!-- /.col -->
                <div class="col-sm-5">
                    <div class="widget-box">
                        <div class="widget-header widget-header-flat widget-header-small">
                            <h5 class="widget-title">
                                <i class="ace-icon fa fa-signal"></i>
                                Traffic Sources
                            </h5>

                            <div class="widget-toolbar no-border">
                                <div class="inline dropdown-hover">
                                    <button class="btn btn-minier btn-primary">
                                        This Week
                                        <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                                        <li class="active">
                                            <a href="#" class="blue">
                                                <i class="ace-icon fa fa-caret-right bigger-110">&nbsp;</i>
                                                This Week
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
                                                Last Week
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
                                                This Month
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
                                                Last Month
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <div id="piechart-placeholder"></div>

                                <div class="hr hr8 hr-double"></div>

                                <div class="clearfix">
                                    <div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-facebook-square fa-2x blue"></i>
																&nbsp; likes
															</span>
                                        <h4 class="bigger pull-right">1,255</h4>
                                    </div>

                                    <div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-twitter-square fa-2x purple"></i>
																&nbsp; tweets
															</span>
                                        <h4 class="bigger pull-right">941</h4>
                                    </div>

                                    <div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-pinterest-square fa-2x red"></i>
																&nbsp; pins
															</span>
                                        <h4 class="bigger pull-right">1,050</h4>
                                    </div>
                                </div>
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
            _this.drawPieChart();
        },
        methods:{
            drawSaleChart() {
                // 生成随机两组数据
                let d1 = [];
                for (let i = 0; i < 30; i += 1) {
                    d1.push([i + 1, 2000 + Math.floor((Math.random()*100)+1)]);
                }
                let d2 = [];
                for (let i = 0; i < 30; i += 1) {
                    d2.push([i + 1, 1900 + Math.floor((Math.random()*100)+1)]);
                }

                let sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
                $.plot("#sales-charts", [
                    { label: "最近30天", data: d1 },
                    { label: "上一周期", data: d2 },
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
                        tickLength: 0
                    },
                    grid: {
                        backgroundColor: { colors: [ "#fff", "#fff" ] },
                        borderWidth: 1,
                        borderColor:'#555'
                    }
                });
            },
            drawPieChart(){
                function drawPieChart(placeholder, data, position) {
                    $.plot(placeholder, data, {
                        series: {
                            pie: {
                                show: true,
                                tilt:0.8,
                                highlight: {
                                    opacity: 0.25
                                },
                                stroke: {
                                    color: '#fff',
                                    width: 2
                                },
                                startAngle: 2
                            }
                        },
                        legend: {
                            show: true,
                            position: position || "ne",
                            labelBoxBorderColor: null,
                            margin:[-30,15]
                        }
                        ,
                        grid: {
                            hoverable: true,
                            clickable: true
                        }
                    })
                }
                drawPieChart(placeholder, data);

                /**
                 we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
                 so that's not needed actually.
                 */
                placeholder.data('chart', data);
                placeholder.data('draw', drawPieChart);


                //pie chart tooltip example
                var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
                var previousPoint = null;

                placeholder.on('plothover', function (event, pos, item) {
                    if(item) {
                        if (previousPoint != item.seriesIndex) {
                            previousPoint = item.seriesIndex;
                            var tip = item.series['label'] + " : " + item.series['percent']+'%';
                            $tooltip.show().children(0).text(tip);
                        }
                        $tooltip.css({top:pos.pageY + 10, left:pos.pageX + 10});
                    } else {
                        $tooltip.hide();
                        previousPoint = null;
                    }

                });
            }
        }

    }
</script>
