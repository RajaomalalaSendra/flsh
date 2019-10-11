<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import ="com.flsh.model.User" %>
<% User onlineUser = (User) request.getSession().getAttribute("user"); %>
<div class="header-advance-area">
    <div class="header-top-area">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="header-top-wraper">
                        <div class="row">
                            <div class="col-lg-1 col-md-0 col-sm-1 col-xs-12">
                                <div class="menu-switcher-pro">
                                    <button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
										<i class="educate-icon educate-nav"></i>
									</button>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-7 col-sm-6 col-xs-12">
                                <div class="header-top-menu tabl-d-n">
                                    <ul class="nav navbar-nav mai-top-nav">
                                        <li class="nav-item"><a href="/scolarLMD/" class="nav-link">Home</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                                <div class="header-right-info">
                                    <ul class="nav navbar-nav mai-top-nav header-right-menu">
                                        <!--<li class="nav-item dropdown">
                                            <a href="#" data-toggle="dropdown" role="button" aria-expanded="false" class="nav-link dropdown-toggle"><i class="educate-icon educate-message edu-chat-pro" aria-hidden="true"></i><span class="indicator-ms"></span></a>
                                            <div role="menu" class="author-message-top dropdown-menu animated zoomIn">
                                                <ul class="message-menu">
                                                    <li>
                                                        <a href="#">
                                                            <div class="message-content">
                                                                <span class="message-date">2018/2019</span>
                                                                <h2>18/19</h2>
                                                                <p>All the database of 2018/2019.</p>
                                                            </div>
                                                        </a>
                                                    </li>
                                                </ul>
                                            
                                            </div>
                                        </li>-->
                                        <li class="nav-item">
                                            <a href="#" data-toggle="dropdown" role="button" aria-expanded="false" class="nav-link dropdown-toggle">
											<span class="admin-name" ><%= onlineUser != null ? onlineUser.getUsername() +" ( "+onlineUser.getTypeComputed()+" )" : "User" %></span>
											<i class="fa fa-angle-down edu-icon edu-down-arrow"></i>
										</a>
                                            <ul role="menu" class="dropdown-header-top author-log dropdown-menu animated zoomIn">
                                                <li><a class = "detail-user" href="<c:url value='/myAccount' />"><span class="edu-icon edu-home-admin author-log-ic"></span>My Account</a></li>
                                                <li><a href="<c:url value='/signout' />"><span class="edu-icon edu-locked author-log-ic"></span>Log Out</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
 </div>
 <!-- Mobile Menu start -->
    <div class="mobile-menu-area">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="mobile-menu">
                        <nav id="dropdown">
                            <ul class="mobile-menu-nav">
                                <li><a data-toggle="collapse" data-target="#Charts" href="<c:url value='/' />">Education <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
                                    <ul class="collapse dropdown-header-top">
                                        <li><a href="<c:url value='/' />">Dashboard</a></li>
                                        <% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
	                                        <li><a href="<c:url value='/educations/cyclesandlevel/' />">Cycle & Level</a></li>
	                                        <li><a href="<c:url value='/educations/periods/' />">Periods</a></li>
                                        <% } %>
                                        <% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) <= 2) { %>
                                        	<li><a href="<c:url value='/educations/notes/' />">Notes</a></li>
                                        <% } %>
                                        <% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
                                        	<li><a href="<c:url value='/educations/deliberation' />">Deliberation</a></li>
                                        <% } %>
                                    </ul>
                                </li>
                                <li><a data-toggle="collapse" data-target="#demoevent" href="#">Teachers <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
                                    <ul id="demoevent" class="collapse dropdown-header-top">
                                    	<% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) != 2) { %>
                                        	<li><a href="<c:url value='/professors' />">All Teachers</a></li>
                                        <% } %>
                                        <li><a href="<c:url value='/professor/courses' />">Teacher-Courses</a></li>
                                    </ul>
                                </li>
                                <li><a data-toggle="collapse" data-target="#demopro" href="#">Students <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
                                    <ul id="demopro" class="collapse dropdown-header-top">
                                    	<% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) != 2) { %>
                                        	<li><a href="<c:url value='/students' />">All Students</a></li>
                                        <% } %>
                                        <li><a href="<c:url value='/students/subscriptions' />">Level-Students</a></li>
                                    </ul>
                                </li>
                                <% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) <= 2) { %>
	                                <li><a data-toggle="collapse" data-target="#democrou" href="#">Courses <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
	                                    <ul id="democrou" class="collapse dropdown-header-top">
	                                        <li><a href="<c:url value='/ue' />">UE & EC</a></li>
	                                        <li><a href="<c:url value='/course/byPeriod' />">Periodical courses</a></li>
	                                    </ul>
	                                </li>
                                <% }  %>
                                <% if( onlineUser != null && onlineUser.isEnabled() && Integer.parseInt( onlineUser.getType()) == 1) { %>
	                                <li><a data-toggle="collapse" data-target="#users_all" href="#">Users <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
	                                    <ul id="demodepart" class="collapse dropdown-header-top">
	                                        <li><a href="<c:url value='/users' />">All users</a>
	                                        </li>
	                                    </ul>
	                                </li>
                                <% } %>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Mobile Menu end -->