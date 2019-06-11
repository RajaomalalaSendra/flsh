<div class="footer-copyright-area">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="footer-copy-right">
                    <p>Copyright © FLSH 2019. All rights reserved.</p>
                </div>
            </div>
        </div>
    </div>
    <c:set var="req" value="${pageContext.request}" />
    <input type = "hidden" id = "base-url" value = "${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />
</div>