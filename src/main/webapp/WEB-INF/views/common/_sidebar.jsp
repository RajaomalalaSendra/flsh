<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--[if lt IE 8]>
	<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

<!-- Start Left menu area -->
<div class="left-sidebar-pro">
    <nav id="sidebar" class="">
        <div class="sidebar-header">
            <a href="index.html"><img class="main-logo" src="img/logo/logo.png" alt="" /></a>
            <strong><a href="index.html"><img src="img/logo/logosn.png" alt="" /></a></strong>
        </div>
        <div class="left-custom-menu-adp-wrap comment-scrollbar">
            <nav class="sidebar-nav left-sidebar-menu-pro">
                <ul class="metismenu" id="menu1">
                    <li class="active">
                        <a class="has-arrow" href="index.html">
						   <span class="educate-icon educate-home icon-wrap"></span>
						   <span class="mini-click-non">Education</span>
						</a>
                        <ul class="submenu-angle" aria-expanded="true">
                            <li><a title="Dashboard" href="<c:url value='/' />"><span class="mini-sub-pro">Dashboard</span></a></li>
                            <li><a title="Cycle&&Level" href="<c:url value='/educations/cyclesandlevel/' />"><span class="mini-sub-pro">Cycle & Level</span></a></li>
                            <li><a title="Period" href="<c:url value='/educations/periods/' />"><span class="mini-sub-pro">Periods</span></a></li>
                            <li><a title="Notes" href="<c:url value='/educations/notes' />"><span class="mini-sub-pro">Notes</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="has-arrow" href="all-professors.html" aria-expanded="false"><span class="educate-icon educate-professor icon-wrap"></span> <span class="mini-click-non">Professors</span></a>
                        <ul class="submenu-angle" aria-expanded="false">
                            <li><a title="All Professors" href="<c:url value='/professors' />"><span class="mini-sub-pro">All Professors</span></a></li>
                            <li><a title="Add Professor" href="professor/add"><span class="mini-sub-pro">Add Professor</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="has-arrow" href="all-students.html" aria-expanded="false"><span class="educate-icon educate-student icon-wrap"></span> <span class="mini-click-non">Students</span></a>
                        <ul class="submenu-angle" aria-expanded="false">
                            <li><a title="All Students" href="<c:url value='/students' />"><span class="mini-sub-pro">All Students</span></a></li>
                            <li><a title="Students Profile" href="<c:url value='/students/subscriptions' />"><span class="mini-sub-pro">Manage subscriptions</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="has-arrow" href="all-courses.html" aria-expanded="false"><span class="educate-icon educate-course icon-wrap"></span> <span class="mini-click-non">Courses</span></a>
                        <ul class="submenu-angle" aria-expanded="false">
                            <li><a title="All UE/EC" href="<c:url value='/ue' />"><span class="mini-sub-pro">All UE/EC</span></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="has-arrow" href="all-courses.html" aria-expanded="false"><span class="educate-icon educate-library icon-wrap"></span> <span class="mini-click-non">Users</span></a>
                        <ul class="submenu-angle" aria-expanded="false">
                            <li><a title="All Secretaries" href="<c:url value='/users' />"><span class="mini-sub-pro">All users</span></a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </nav>
</div>
<!-- End Left menu area -->