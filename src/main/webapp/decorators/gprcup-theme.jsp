<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title><decorator:title/></title>

<c:url var="global9css" value="/images/global9.min.css"/>
<c:url var="fchatcss" value="/images/fchat.css"/>
	
<link href="${global9css}" media="screen" rel="stylesheet" type="text/css">
<link href="${fchatcss}"   media="all"    rel="stylesheet" type="text/css" id="arrowchat_css" charset="utf-8">

<!-- favicon -->
<c:url var="faviconUrl" value="/favicon.ico"/>
<link rel="icon" type="image/x-icon" href="${faviconUrl}"/>

<c:url var="jQueryMinUrl" value="/images/jquery.min.js"/>
<script src="${jQueryMinUrl}"/>

<script src="/gprdata/images/jquery-ui.min.js"></script>
<link href="/gprdata/images/perfect-scrollbar.css" rel="stylesheet">
<script src="/gprdata/images/perfect-scrollbar.min.js"></script>
<style type="text/css">
	.cup {border-top:1px solid #000; border-left:1px solid #000; width:100%; font-size:1em;}
	.cup th, .cup td {border-right:1px solid #000; border-bottom:1px solid #000; padding:4px;}
	.cup th {background:#1b293d; font-weight:bold; font-size:1.1em;}
	.cup td.arrow {background:url(/gprdata/images/arrow.gif) no-repeat left center; padding-left:12px;}
	.cup td.score {background:#1b293d; width:13px; text-align:center; font-weight:bold;}
	.cup td.scoreW {background:#1b293d; width:13px; text-align:center; font-weight:bold; color: white;}	
	.cup td.even {background-color:#4e76ac;}
	.cup td.even2 {background-color:#7a8ea9; color:#1b293d;}
	span.cup {
	    float: none!important;
	    position: relative;
	    width: 27px;
	    text-align: left;
	    vertical-align: top;
	    height: 13px;
	    background: url(/gprdata/images/cupbg.gif) no-repeat left top;
	    display: inline-block;
	    color: #fff;
	    font: bold 10px arial;
	    margin: 0 0 0 3px;
	    padding: 0;
	    line-height: 13px;
	    text-indent: 17px;
	    text-shadow: 1px 1px 0 #735001;
	}
</style>
</head>

<body class="lang_gb">

<c:url var="mainAction" value="/main.html"/>
	
<c:url value="/perform_logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

<span style="float: right">
	<a href="${mainAction}"><spring:message code="label.home"/></a>
    <a href="?lang=en">en</a> 
    <a href="?lang=es">es</a>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<a href="javascript:logout()">Logout (${pageContext.request.userPrincipal.name})</a>
	</c:if>	
</span>

</form>


<script type="text/javascript">
function logout() {
	document.forms['logout'].submit();
}
</script>

<div id="outer">
		
<div id="content">
<div id="infobar">

<div id="info_right">
</div>
</div>
				
<div id="contentinner"><span class="tl cc"></span> <span class="tr cc"></span><span class="bl cc"></span> <span class="br cc"></span>
<div class="inner">	

<div align="center">	
<div class="navholder">

<img src="/gprdata/images/GPR-Logo.jpg">

</div>

<br><br><br><br>

<div align="center" style="margin-bottom:5px;margin-top:5px;"></div>

<h1 class="block"><span class="tl bc"></span><span class="tr bc"></span><span class="bl bc"></span><span class="br bc"></span>
Gulf Porsche Cup Standings ${currentSeason.nameSeason}
<a href="" title="Previous season" alt="Previous season" class="prev">«</a>
<a href="" title="Next season" alt="Next season" class="next">»</a>
</h1>

<br>

<!-- START CONTENT -->			  
<decorator:body/>
<!-- END CONTENT -->

</div>
<div class="clear"></div>
</div>
</div>
<div class="clear"></div>
</div>

</div>
</body>
</html>