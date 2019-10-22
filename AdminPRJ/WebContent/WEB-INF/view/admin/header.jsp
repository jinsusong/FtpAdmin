<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String name = (String)session.getAttribute("name");
%>

<% if(name == null){%>
<script>
	console.log("<%=name %>");
	location.href="/signIn.do";
</script>
<%}%>

<header class="topbar">
	<nav class="navbar top-navbar navbar-toggleable-sm navbar-light">
		<!-- ============================================================== -->
		<!-- Logo -->
		<!-- ============================================================== -->
		<div class="navbar-header">
			<a class="navbar-brand" href="index.html"> <!-- Logo icon --> 
				<b>
					<!--You can put here icon as well // <i class="wi wi-sunset"></i> //-->
					<!-- Dark Logo icon --> 
					<img src="/assets/images/logo-icon.png"	alt="homepage" class="dark-logo" />
				</b> <!--End Logo icon --> <!-- Logo text --> 
				<span> <!-- dark Logo text -->
					<!-- <img src="/assets/images/logo-text.png" alt="homepage" class="dark-logo" /> -->
					<img src="/assets/images/qLogo.png" alt="homepage" class="dark-logo" style="height:33px; width:182px;"/>
				</span>
			</a>
		</div>
		<!-- ============================================================== -->
		<!-- End Logo -->
		<!-- ============================================================== -->
		<div class="navbar-collapse">
			<!-- ============================================================== -->
			<!-- toggle and nav items -->
			<!-- ============================================================== -->
			<ul class="navbar-nav mr-auto mt-md-0 ">
				<!-- This is  -->
				<li class="nav-item"><a
					class="nav-link nav-toggler hidden-md-up text-muted waves-effect waves-dark"
					href="javascript:void(0)"><i class="ti-menu"></i></a></li>
				<li class="nav-item hidden-sm-down">
					<form class="app-search p-l-20">
						<input type="text" class="form-control"
							placeholder="Search for..."> <a class="srh-btn"><i
							class="ti-search"></i></a>
					</form>
				</li>
			</ul>
			<!-- ============================================================== -->
			<!-- User profile and search -->
			<!-- ============================================================== -->
			<ul class="navbar-nav my-lg-0">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle text-muted waves-effect waves-dark"
						href="" data-toggle="dropdown" aria-haspopup="true"	aria-expanded="false">
						<img src="/assets/images/users/1.jpg" alt="user" class="profile-pic m-r-5" />
						<%if(!"".equals(name)){%>
							<b><%=name  + "님"%></b>
							<button type="button" class="btn-default" onclick="location.href='/signOut.do'" style="background-color: white;">로그아웃</a>
						<%}else{%>
							<a href="/signIn.do">로그인</button>
						<%} %>
					</a>
				</li>
			</ul>
		</div>
	</nav>
</header>