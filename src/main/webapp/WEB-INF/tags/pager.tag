<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

<c:set var="range" scope="page" value="${ pageDTO.maxPageNumber < 5 ? pageDTO.maxPageNumber : 5 }"/>

<c:set var="half" scope="page" value="${ range / 2 }"/>
<%-- without the following line, 6 page number are displayed instead of five --%>
<%-- indeed, the numbers in JSPs are Double by default, so %1 removes the digits after the comma --%>
<c:set var="half" scope="page" value="${ half - (half % 1) }"/>

<c:set var="start" scope="page" value="${ pageDTO.currentPageNumber - half }"/>
<c:set var="stop" scope="page" value="${ pageDTO.currentPageNumber + half }"/>

<c:set var="tmp" scope="page" value="${ start }"/>

<c:set var="start" scope="page" value="${ tmp <= 0 ? 0 : start }"/>
<c:set var="stop" scope="page" value="${ tmp <= 0 ? range-1 : stop }"/>

<c:set var="tmp" scope="page" value="${ stop }"/>
<c:set var="stop" scope="page" value="${ tmp > pageDTO.maxPageNumber-1 ? pageDTO.maxPageNumber-1 : stop }"/>
<c:set var="stop" scope="page" value="${ stop < 0 ? 0 : stop }"/>

<ul class="pagination">
    <li>
        <a href='<cst:links target="self" pageNb="0" />' aria-label="Previous">
            <span aria-hidden="true">First</span>
        </a>
    </li>
    <li>
        <a href='<cst:links target="self" pageNb="${ pageDTO.currentPageNumber-1 < 0 ? 0 : pageDTO.currentPageNumber-1 }" />'
           aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
        </a>
    </li>

    <%-- the current page is not displayed as a link--%>
    <c:forEach var="i" begin="${ start }" end="${ stop }" step="1">
        <c:choose>
            <c:when test="${ i == pageDTO.currentPageNumber }">
                <li><a class="disabled" style="color: black"><c:out value="${ i + 1 }"/></a></li>
            </c:when>
            <c:otherwise>
                <li><a href='<cst:links target="self" pageNb="${ i }" />'><c:out value="${ i + 1 }"/></a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <li>
        <a href='<cst:links target="self" pageNb="${ pageDTO.currentPageNumber+1 > pageDTO.maxPageNumber-1 ? pageDTO.maxPageNumber-1 : pageDTO.currentPageNumber+1 }" />'
           aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
        </a>
    </li>
    <li>
        <a href='<cst:links target="self" pageNb="${ pageDTO.maxPageNumber < 0 ? stop : pageDTO.maxPageNumber-1 }" />'
           aria-label="Previous">
            <span aria-hidden="true">Last</span>
        </a>
    </li>
</ul>