<%@page import="poly.dto.BoardDTO"%>
<%@page import="poly.dto.FtphistoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<FtphistoryDTO> fileList=	(List<FtphistoryDTO>)request.getAttribute("fileList");
	BoardDTO paging=	(BoardDTO)request.getAttribute("paging");
	
%>

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
	                                		><%=fileList.get(i).getList_name()[j] %></a>
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
							  <%--   <li class='box arrow left'>
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
							   <%--  <li class='box arrow right'>
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
