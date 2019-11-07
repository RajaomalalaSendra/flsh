<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id = "simple-down">
	<div class="analytics-sparkle-area">
	  <c:forEach items = "${ cycles }" var = "cycle" varStatus = "status">
	    <div class="container-fluid">
			<h3>${ cycle.getCycleLibelle() }</h3>
	        <div class="row">
				<c:forEach items = "${ cycle.getLevelsNumber() }" var = "level" varStatus = "status">
			        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12">
		                <div class="analytics-sparkle-line reso-mg-b-30">
		                    <div class="analytics-content">                    
		                        <h5>${ level.key }</h5>
		                        <h2><span class="counter">${ level.value }</span> <span class="tuition-fees">Etudiants</span></h2>
		                        <span class="text-success">${ cycle.getPercent(level.key) }%</span>
		                        <div class="progress m-b-0">
		                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100" style="width:${ cycle.getPercent(level.key) }%;"> </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		         </c:forEach>
	         </div>
	    </div>
	   </c:forEach>
	</div>        
</div>