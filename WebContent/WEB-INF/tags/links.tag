<jsp:directive.tag body-content="empty"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="linkTo" required="true" %>
<%@attribute name="search" required="false" %>
<%@attribute name="pageNb" required="false" %>
<%@attribute name="displayBy" required="false" %>
<%@attribute name="field" required="false" %>
<%@attribute name="ascending" required="false" %>

<c:set var="pathDash" value="/computer-database/access"/>
<c:set var="pathAdd" value="/computer-database/access/add"/>
<c:set var="pathEdit" value="/computer-database/access/edit"/>
<c:set var="pathSearch" value="/computer-database/access/search"/>
<c:set var="pathReset " value="/computer-database/access?reset=true"/>

<c:set var="emptyText" value=""/>
<c:set var="tmpPath" value=""/>
<c:set var="tmpSearchMode" value="${ false }"/>

<c:set var="tmpPageNb"
       value="${ emptyText.concat('?pageNb=').concat(pageDTO.currentPageNumber) }"/>
<c:set var="tmpDisplayBy"
       value="${ emptyText.concat('&displayBy=').concat(pageDTO.objectsPerPage) }"/>

<%-- --%>
<c:choose>
    <c:when test="${not empty linkTo}">
        <c:choose>
            <c:when
                    test="${ linkTo.equals('dashboard') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat('/computer-database/access') }"/>
            </c:when>
            <c:when test="${ linkTo.equals('add') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat('/computer-database/access/add') }"/>
            </c:when>
            <c:when test="${ linkTo.equals('edit') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat('/computer-database/access/edit') }"/>
            </c:when>
            <c:when test="${ linkTo.equals('search') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat('/computer-database/access/search') }"/>
            </c:when>
            <c:when test="${ linkTo.equals('reset') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat('/computer-database/access?') }"/>
                <c:set var="tmpPageNb"
                       value="${ emptyText.concat('?pageNb=0') }"/>
                <c:set var="tmpDisplayBy"
                       value="${ emptyText.concat('&displayBy=10') }"/>
            </c:when>
            <c:when test="${ linkTo.equals('self') }">
                <c:set var="tmpPath" value="${ tmpPath.concat(currentPath) }"/>
            </c:when>
            <c:otherwise>
                <c:out value="${ tmpPath.concat(pathDash) }"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:out value="${ tmpPath.concat(pathDash) }"/>
    </c:otherwise>
</c:choose>

<c:if test="${ not empty pageNb and pageNb.matches('[0-9]+') }">
    <c:set var="tmpPageNb"
           value="${ emptyText.concat('?pageNb=').concat(pageNb) }"/>
</c:if>

<c:if test="${ not empty displayBy and displayBy.matches('[0-9]+') }">
    <c:set var="tmpDisplayBy" value="${ emptyText.concat('&displayBy=').concat(displayBy) }"/>
</c:if>

<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }"/>
<c:set var="tmpPath" value="${ tmpPath.concat(tmpDisplayBy) }"/>

<c:out value="${ tmpPath }" escapeXml="false"/>