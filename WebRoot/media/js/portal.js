var Portal = function () {

    return {

        //main function to initiate the module
        init: function () {
            App.addResponsiveHandler(function () {
            	// Portal.initPieCharts();
            });
        },

        initMiniCharts: function () {
             
            $('.easy-pie-chart .number.transactions').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('yellow')
            });

            $('.easy-pie-chart .number.visits').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('green')
            });
             
            $('.easy-pie-chart .number.bounce').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('red')
            });
            
            $('.easy-pie-chart .number.points').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('blue')
            });

            $('.easy-pie-chart-reload').click(function(){
                $('.easy-pie-chart .number').each(function() {
                    var newValue = Math.floor(100*Math.random());
                    $(this).data('easyPieChart').update(newValue);
                    $('span', this).text(newValue);
                });
            });
        },
        
        initPieCharts: function (datas) {

        	var data = [];
            var percent = 0;
            var series = datas.length;
            
            for (var i = 0; i < series; i++)
            {
            	percent = datas[i]["PRONUM"] / datas[i]["ALLNUM"];
            	data[i] = {
            		label: datas[i]["PROVINCE"],
            		data: Math.round(percent * 100) / 100
            	}
            }
            
            // GRAPH 4
            $.plot($("#pie_chart_4"), data, {
            	series: {
            		pie: {
            			show: true,
            			radius: 1,
            			label: {
            				show: true,
            				radius: 2 / 3,
            				formatter: function (label, series) {
            					return '<div style="font-size:8pt;text-align:center;padding:2px;color:#E6E6FA;">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
            				},
            				/*background: {
            					opacity: 0.1,
            					color: '#000'
            				},*/
            				threshold: 0.04
            			}
            		}
            	},
            	legend: {
            		show: false
            	}
            });

            // GRAPH 6
            $.plot($("#pie_chart_6"), data, {
            	series: {
            		pie: {
            			show: true,
            			radius: 1,
            			label: {
            				show: true,
            				radius: 2 / 3,
            				formatter: function (label, series) {
            					return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
            				},
            				threshold: 0.05
            			}
            		}
            	},
            	legend: {
            		show: false
            	}
            });
        },
        
        initChart: function()
        {
        	function chart2() {
                function randValue() {
                    return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
                }
                var pageviews = [
                    [1, randValue()],
                    [2, randValue()],
                    [3, 2 + randValue()],
                    [4, 3 + randValue()],
                    [5, 5 + randValue()],
                    [6, 10 + randValue()],
                    [7, 15 + randValue()],
                    [8, 20 + randValue()],
                    [9, 25 + randValue()],
                    [10, 30 + randValue()],
                    [11, 35 + randValue()],
                    [12, 25 + randValue()],
                    [13, 15 + randValue()],
                    [14, 20 + randValue()],
                    [15, 45 + randValue()],
                    [16, 50 + randValue()],
                    [17, 65 + randValue()],
                    [18, 70 + randValue()],
                    [19, 85 + randValue()],
                    [20, 80 + randValue()],
                    [21, 75 + randValue()],
                    [22, 80 + randValue()],
                    [23, 75 + randValue()],
                    [24, 70 + randValue()],
                    [25, 65 + randValue()],
                    [26, 75 + randValue()],
                    [27, 80 + randValue()],
                    [28, 85 + randValue()],
                    [29, 90 + randValue()],
                    [30, 95 + randValue()]
                ];

                var plot = $.plot($("#chart_2"), [{
                            data: pageviews,
                            label: "数据统计"
                        }
                    ], {
                        series: {
                            lines: {
                                show: true,
                                lineWidth: 2,
                                fill: true,
                                fillColor: {
                                    colors: [{
                                            opacity: 0.05
                                        }, {
                                            opacity: 0.01
                                        }
                                    ]
                                }
                            },
                            points: {
                                show: true
                            },
                            shadowSize: 2
                        },
                        grid: {
                            hoverable: true,
                            clickable: true,
                            tickColor: "#eee",
                            borderWidth: 0
                        },
                        colors: ["#37b7f3", "#52e136"],
                        xaxis: {
                            ticks: 11,
                            tickDecimals: 0
                        },
                        yaxis: {
                            ticks: 11,
                            tickDecimals: 0
                        }
                    });

            }
        	chart2();
        }
    };

}();