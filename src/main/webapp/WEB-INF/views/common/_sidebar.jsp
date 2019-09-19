<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import ="com.flsh.model.User" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<!--[if lt IE 8]>
	<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

<!-- Start Left menu area -->
<div class="left-sidebar-pro">
    <nav id="sidebar" class="">
        <div class="sidebar-header">
            <a href="<c:url value='/' />"><img class="main-logo" src="<c:url value="/resources/img/logo/logo.png" />" alt="" /></a>
            <strong><a href="<c:url value='/' />"><img src="<c:url value="/resources/img/logo/logosn.png" />" alt="" /></a></strong>
        </div>
        <div class="left-custom-menu-adp-wrap comment-scrollbar">
            <nav class="sidebar-nav left-sidebar-menu-pro">
                <ul class="metismenu" id="menu1">
                    <li class="${ menu == 'education' || menu == null ? 'active' : '' }">
                        <a class="has-arrow" href="index.html">
						   <span class="educate-icon educate-home icon-wrap"></span>
						   <span class="mini-click-non">Education</span>
						</a>
                        <ul class="submenu-angle" aria-expanded="${ menu == 'education' || menu == null ? 'true' : 'false' }">
                            <li class = "${ submenu == 'dashboard'? 'active' : '' }"><a title="Dashboard" href="<c:url value='/' />"><span class="mini-sub-pro">Dashboard ${ user.getUsername() } ${ user.isEnabled() }</span></a></li>
                            <% if(user != null && user.isEnabled() && Integer.parseInt( user.getType()) == 1 ) { %>
                            	<li class = "${ submenu == 'cycle_level_parcours'? 'active' : '' }"><a title="Cycle&&Level" href="<c:url value='/educations/cyclesandlevel/' />"><span class="mini-sub-pro">Cycle & Level</span></a></li>
                            	<li class = "${ submenu == 'periods'? 'active' : '' }"><a title="Period" href="<c:url value='/educations/periods/' />"><span class="mini-sub-pro">Periods</span></a></li>
                            <% } %>
                            <% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) <= 2) { %>
                            	<li class = "${ submenu == 'notes'? 'active' : '' }"><a title="Notes" href="<c:url value='/educations/notes' />"><span class="mini-sub-pro">Notes</span></a></li>
                            <% } %>
                            <% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) == 1) { %>
                            	<li class = "${ submenu == 'deliberation'? 'active' : '' }"><a title="Déliberation" href="<c:url value='/educations/deliberation' />"><span class="mini-sub-pro">Deliberation</span></a></li>
                            <% } %>
                        </ul>
                    </li>
                    <li  class="${ menu == 'professor'? 'active' : '' }">
                        <a class="has-arrow" href="all-professors.html" aria-expanded="false"><span class="educate-icon educate-professor icon-wrap"></span> <span class="mini-click-non">Professors</span></a>
                        <ul class="submenu-angle" aria-expanded="${ menu == 'professor'? 'true' : 'false' }">
                        	<% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) != 2) { %>
                            	<li class = "${ submenu == 'all_professors'? 'active' : '' }"><a title="Tous les professeurs" href="<c:url value='/professors' />"><span class="mini-sub-pro">All Professors</span></a></li>
                            <% } %>
                            <li class = "${ submenu == 'prof_courses'? 'active' : '' }"><a title="Cours professeurs" href="<c:url value='/professor/courses' />"><span class="mini-sub-pro">Professor-Courses</span></a></li>
                        </ul>
                    </li>
                    <li class = "${ menu == 'student'? 'active' : '' }">
                        <a class="has-arrow" href="all-students.html" aria-expanded="false"><span class="educate-icon educate-student icon-wrap"></span> <span class="mini-click-non">Students</span></a>
                        <ul class="submenu-angle" aria-expanded="${ menu == 'student'? 'true' : 'false' }">
                        	<% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) != 2) { %>
                            	<li class = "${ submenu == 'all_students'? 'active' : '' }"><a title="All Students" href="<c:url value='/students' />"><span class="mini-sub-pro">All Students</span></a></li>
                            <% } %>
                            <li  class = "${ submenu == 'subscription'? 'active' : '' }"><a title="Inscription des étudiants" href="<c:url value='/students/subscriptions' />"><span class="mini-sub-pro">Level-Students</span></a></li>
                        </ul>
                    </li>
                    <% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) <= 2) { %>
	                    <li class = "${ menu == 'course'? 'active' : '' }">
	                        <a class="has-arrow" href="all-courses.html" aria-expanded="false"><span class="educate-icon educate-course icon-wrap"></span> <span class="mini-click-non">Courses</span></a>
	                        <ul class="submenu-angle" aria-expanded="${ menu == 'course'? 'true' : 'false' }">
	                            <li class = "${ submenu == 'ue_ec'? 'active' : '' }"><a title="All UE/EC" href="<c:url value='/ue' />"><span class="mini-sub-pro">All UE/EC</span></a></li>
	                            <li class = "${ submenu == 'period_course'? 'active' : '' }"><a title="Cours périodique" href="<c:url value='/course/byPeriod' />"><span class="mini-sub-pro">Periodical courses</span></a></li>
	                        </ul>
	                    </li>
                    <% } %>
                    <% if( user != null && user.isEnabled() && Integer.parseInt( user.getType()) == 1) { %>
	                    <li class = "${ menu == 'system'? 'active' : '' }">
	                        <a class="has-arrow" href="all-courses.html" aria-expanded="false"><span class="educate-icon educate-library icon-wrap"></span> <span class="mini-click-non">Users</span></a>
	                        <ul class="submenu-angle" aria-expanded="${ menu == 'system'? 'true' : 'false' }">
	                            <li class = "${ submenu == 'all_users'? 'active' : '' }"><a title="Users admin and direction" href="<c:url value='/users' />"><span class="mini-sub-pro">All users</span></a></li>
	                        </ul>
	                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </nav>
</div>
<!-- End Left menu area -->