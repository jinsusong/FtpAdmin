<%@page import="poly.dto.BoardDTO"%>
<%@page import="poly.dto.FtphistoryDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="/assets/images/favicon.png">
    <title>Home</title>
    <!-- Bootstrap Core CSS -->
    <link href="/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/css/style.css" rel="stylesheet">
    <!-- You can change the theme colors from here -->
    <link href="/css/colors/blue.css" id="theme" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	
	<!-- ajax -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	
	<!-- paging script, css -->
	<%@ include file="/WEB-INF/view/admin/pagingScript.jsp" %>
	
    <%
		List<FtphistoryDTO> fileList=	(List<FtphistoryDTO>)request.getAttribute("fileList");
		BoardDTO paging=	(BoardDTO)request.getAttribute("paging");
	%>
	
</head>
<body class="fix-header fix-sidebar card-no-border">
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper">
        <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
        <%@ include file="/WEB-INF/view/admin/header.jsp" %>
       
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->
   
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <%@ include file="/WEB-INF/view/admin/aside.jsp" %>
      
        <!-- ============================================================== -->
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
       
        <!-- ============================================================== -->
        <!-- Page wrapper  -->
        <!-- ============================================================== -->
        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->
                <div class="row page-titles">
                    <div class="col-md-6 col-8 align-self-center">
                        <h3 class="text-themecolor m-b-0 m-t-0">Dashboard</h3>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="javascript:void(0)">Home</a></li>
                            <li class="breadcrumb-item active">Dashboard</li>
                        </ol>
                    </div>
                    
                </div>
                <!-- ============================================================== -->
                <!-- End Bread crumb and right sidebar toggle -->
                <!-- ============================================================== -->

                <!-- ============================================================== -->
                <!-- Start Page Content -->
               	<%@ include file="/WEB-INF/view/admin/today.jsp" %>
            <%--   	<%@ include file="/WEB-INF/view/admin/table.jsp" %> --%>
            <div id="fList">
				<!-- Row -->
				<div class="row">
				    <div class="col-sm-12">
				        <div class="card">
				            <div class="card-block">
				                <!-- <select class="custom-select pull-right">
				                    <option selected>January</option>
				                    <option value="1">February</option>
				                    <option value="2">March</option>
				                    <option value="3">April</option>
				                </select> -->
				                <h4 class="card-title">FTP HISTORY</h4>
				                <div class="table-responsive m-t-40">
				                    <table class="table stylish-table">
				                        <thead>
				                            <tr>
				                                <th>SEQ</th>
				                                <th>FTP_HOSTNAME</th>
				                                <th>FTP_SEND_DATE</th>
				                                <th>FTP_STATUS</th>
				                                <th>CHG_DT</th>
				                                <th>DOWNLOAD</th>
				                            </tr>
				                        </thead>
				                        <tbody>
				                        <%if(fileList.size() > 0){ %>
				                        	<%for(int i=0; i<fileList.size(); i++){ %>
				                            <tr>
				                                <td style="width:50px;">
					                                <span class="round">
				                                		<%=fileList.get(i).getFtp_seq() %>
					                                </span>
				                                </td>
				                                <td>
				                                    <h6><%=fileList.get(i).getFtp_hostname() %></h6>
				                                    <small class="text-muted"><%=fileList.get(i).getFtp_ip() %></small>
				                                </td>
				                                <td><%=fileList.get(i).getFtp_send_date() %></td>
				                                <td><%=fileList.get(i).getFtp_status() %></td>
				                                <td><%=fileList.get(i).getChg_dt() %></td>
				                                <td>
				                                	<%if(fileList.get(i).getList_name().length > 0){ %>
					                                	<%for(int j=0; j<fileList.get(i).getList_name().length; j++ ) {%>
					                                	<div>
					                                		<a href="JavaScript:ftpDownload('<%=fileList.get(i).getFtp_seq() %>','<%=fileList.get(i).getFtp_send_date() %>','<%=fileList.get(i).getFtp_status() %>','<%=fileList.get(i).getList_name()[j] %>' );"
					                                		style="color: #54667a;"
					                                		><%=(fileList.get(i).getList_name()[j]).substring(11) %></a>
					                               	 	</div>
					                               	 	<% }%>
				                                	<% }else{%>
				                                		not exist
				                                	<% }%>
				                                
				                                </td>
				                                <%-- <td><button type="button" class="btn btn-default" onClick="JavaScript:ftpDownload('<%=fileList.get(i).getFtp_seq() %>','<%=fList.get(i).getFtp_send_date() %>','<%=fList.get(i).getFtp_status() %>' );" style="background-color: #90a4ae;">다운로드</button></td> --%>
				                            </tr>
				                           <%} %>
				                         <%}else{ %>
				                         <tr>
				                                <td style="width:50px;">
					                                <span class="round">
					                                	0
					                                </span>
				                                </td>
				                                <td>
				                                    <h6>0</h6>
				                                    <small class="text-muted">0</small>
				                                </td>
				                                <td>0</td>
				                                <td>0</td>
				                                <td>0</td>
				                                <td>0</td>
				                            </tr>
				                         <%} %>
				                        </tbody>
				                    </table>
				                    
				                    <!-- 페이징 -->
				                    <div class="Page">
									  <ul class="pagination modal2">
									  <!-- <<, < -->
											<%
												if(paging.isPrev()==true) {
											%>
											  	<li class='box first' >
											      <a href="JavaScript:pagingMove('<%=paging.getEndPage()-10 %>')" aria-label="Previous"><span aria-hidden="true"><i class='fa fa-caret-left'></i><i class='fa fa-caret-left'></i></span></a>
											    </li>
											    <%-- <li class='box arrow left'>
											      <a href="JavaScript:pagingMove('<%=paging.getPage()-1 %>')" aria-label="Previous"><span aria-hidden="true"><i class='fa fa-caret-left'></i></span></a>
											    </li> --%>
											<%}%>
										<!-- 숫자 -->
											<%for(int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
												if(paging.getPage()==i) { %>
											    <li class='active num'><a href="JavaScript:pagingMove('<%=i %>')" class='active'><%=i%></a></li>
												<%} else {%>
											    <li class='num'><a href="JavaScript:pagingMove('<%=i %>')"><%=i%></a></li>
											<%}}%>
										<!-- >, >> -->
											<%if(paging.isNext()==true){ %>
											    <%-- <li class='box arrow right'>
											      <a href="JavaScript:pagingMove('<%=paging.getPage()+1 %>')" aria-label="Previous"><span aria-hidden="true"><i class='fa fa-caret-right'></i></span></a>
											    </li> --%>
											    <li class='box last'>
											      <a href="JavaScript:pagingMove('<%=paging.getEndPage()+1 %>')" aria-label="Previous"><span aria-hidden="true"><i class='fa fa-caret-right'></i><i class='fa fa-caret-right'></i></span></a>
											    </li>
											<%} %>
										  </ul>
									</div>
									
				                    
				                    
				                    
				                </div>
				            </div>
				        </div>
				    </div>
				</div>
				            
            
            
            </div>
                <!-- End PAge Content -->
                <!-- ============================================================== -->
            </div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->

            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
            <%@ include file="/WEB-INF/view/admin/footer.jsp" %>
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->

        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    
    <!-- ============================================================== -->
    <!-- All Jquery -->
    <!-- ============================================================== -->
    
    <script src="/assets/plugins/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="/assets/plugins/bootstrap/js/tether.min.js"></script>
    <script src="/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="/js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="/js/waves.js"></script>
    <!--Menu sidebar -->
    <script src="/js/sidebarmenu.js"></script>
    <!--stickey kit -->
    <script src="/assets/plugins/sticky-kit-master/dist/sticky-kit.min.js"></script>
    <!--Custom JavaScript -->
    <script src="/js/custom.min.js"></script>
    
    <!-- ============================================================== -->
    <!-- This page plugins -->
    <!-- ============================================================== -->
    
    <!-- Flot Charts JavaScript -->
    <script src="/assets/plugins/flot/jquery.flot.js"></script>
    <script src="/assets/plugins/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
    <script src="/js/flot-data.js"></script>
    <!-- ============================================================== -->
    <!-- Style switcher -->
    <!-- ============================================================== -->
    <script src="/assets/plugins/styleswitcher/jQuery.style.switcher.js"></script>
</body>

</html>
