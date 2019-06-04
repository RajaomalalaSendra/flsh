<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Users List</h1>  
<table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Name</th>
      <th scope="col">Mail</th>
      <th scope="col">Type</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
  <tbody>  
   <c:forEach var="users" items="${users}">   
   <tr>  
   <td scope="row">${users.id}</td>  
   <td>${users.username}</td>  
   <td>${users.email}</td>  
   <td>${users.type}</td>  
   <td><a href="/user/detail?id=${users.id}">View</a></td>  
   </tr>  
   </c:forEach>
  </tbody>  
</table>  
   