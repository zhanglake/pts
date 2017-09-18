<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<link href="<c:url value="/media/css/jquery.easy-pie-chart.css"/>" rel="stylesheet" type="text/css" media="screen" />

    
<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
    <div class="span12">
    </div>

</div>

<!-- END PAGE HEADER-->
<div id="dashboard">
     <!-- BEGIN DASHBOARD STATS  -->
    <div class="row-fluid">
        <div class="responsive span3" data-tablet="span6" data-desktop="span3">
            <div class="dashboard-stat blue">
                <div class="visual">
                    <i class="icon-user"></i>
                </div>
                <div class="details">
                    <div class="number">${cmpCount }</div>
                    <div class="desc">企业用户数量</div>
                </div>
            </div>
        </div>

        <div class="responsive span3" data-tablet="span6" data-desktop="span3">
            <div class="dashboard-stat purple">
                <div class="visual">
                    <i class="icon-group"></i>
                </div>
                <div class="details">
                    <div class="number">${dealCount }</div>
                    <div class="desc">经销商数量</div>
                </div>
            </div>
        </div>

        <div class="responsive span3" data-tablet="span6  fix-offset" data-desktop="span3">
            <div class="dashboard-stat yellow">
                <div class="visual">
                    <i class="icon-bar-chart"></i>
                </div>
                <div class="details">
                    <div class="number">${appCount }</div>
                    <div class="desc">APP装机量</div>
                </div>
            </div>
        </div>

        <div class="responsive span3" data-tablet="span6" data-desktop="span3">
            <div class="dashboard-stat green">
                <div class="visual">
                    <i class="icon-qrcode"></i>
                </div>
                <div class="details">
                    <div class="number">${scanCount }</div>
                    <div class="desc">扫码次数</div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row-fluid">
    	<div class="span6">
    		<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption"><i class="icon-reorder"></i>经销商区域分布</div>
					<div class="tools">
						<a href="javascript:;" class="reload"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div id="pie_chart_4" class="chart"></div>
				</div>
			</div>
    	</div>
    	<div class="span6">
    		<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption"><i class="icon-reorder"></i>用户区域分布</div>
					<div class="tools">
						<a href="javascript:;" class="reload"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div id="pie_chart_6" class="chart"></div>
				</div>
			</div>
    	</div>
    </div>
</div>
<!-- END DASHBOARD STATS -->

<script src="<c:url value="/media/fm/jquery.easy-pie-chart.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.flot.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.flot.pie.js"/>" type="text/javascript"></script>

<script src="<c:url value="/media/js/portal.js"/>" type="text/javascript"></script>

<script>
    jQuery(document).ready(function () 
    {
    	Portal.init();
    	Portal.initMiniCharts();
    	
    	$.get("portal/dearlerPercent.do", function(data, status)
    	{
    		Portal.initPieCharts(data);	
    	});
    });
</script>

