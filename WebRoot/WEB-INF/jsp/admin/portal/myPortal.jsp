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
    <!--
        <div class="responsive span3" data-tablet="span6" data-desktop="span3">
            <div class="dashboard-stat blue endCount">
                <div class="visual">
                    <i class="icon-user"></i>
                </div>
                <div class="details">
                    <div class="number">${endCount }</div>
                    <div class="desc">最终用户数量</div>
                </div>
            </div>
        </div>
        -->
        
        <div class="responsive span6" data-tablet="span6" data-desktop="span6">
            <div class="dashboard-stat purple dealerCount">
                <div class="visual">
                    <i class="icon-group"></i>
                </div>
                <div class="details">
                    <div class="number">${dealCount }</div>
                    <div class="desc">经销商数量</div>
                </div>
            </div>
        </div>

        <!--<div class="responsive span3" data-tablet="span6" data-desktop="span3">
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
        -->
        
        <div class="responsive span6" data-tablet="span6  fix-offset" data-desktop="span6">
            <div class="dashboard-stat yellow applyRecord">
                <div class="visual">
                    <i class="icon-foursquare"></i>
                </div>
                <div class="details">
                    <div class="number">${applyCount }</div>
                    <div class="desc">二维码申请记录</div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row-fluid">
    	<div class="row-fluid margin-bottom-40">
    		<div class="span3 pricing hover-effect">
    			<img src="<c:url value="/media/image/tiles/tiles_3.png"/>" />
    		</div>
    		<div class="span3 pricing hover-effect">
    			<img src="<c:url value="/media/image/tiles/tiles_4.png"/>" />
    		</div>
    		<div class="span3 pricing hover-effect">
    			<img src="<c:url value="/media/image/tiles/tiles_5.png"/>" />
    		</div>
    		<div class="span3 pricing hover-effect">
    			<img src="<c:url value="/media/image/tiles/tiles_6.png"/>" />
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
    	
    	/*$.get("portal/dearlerPercent.do", function(data, status)
    	{
    		Portal.initPieCharts(data);	
    	});*/
    	
    	
    	$(".applyRecord").bind("click", function()
    	{
    		App.loadPage("portal/applyPage.do");
    	});
    	
    	$(".endCount").bind("click", function()
    	{
    		App.loadPage("account/userPage.do");
    	});
    	
    	$(".dealerCount").bind("click", function()
    	{
			App.loadPage("dealer/dealerPage.do");
    	});
    	
    	var newApplyCount = ${newApplyCount};
    	$(".badge").html(newApplyCount);
    });
</script>

