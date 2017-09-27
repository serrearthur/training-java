<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="ex"%>
<%@ attribute name="currentpage" required="true"%>
<%@ attribute name="totalpage" required="true"%>
<%@ attribute name="displayrange" required="true"%>

<c:set var="nextpage" value="${currentpage < totalpage ? currentpage +1 : currentpage }"/>
<c:set var="previouspage" value="${currentpage > 1 ? currentpage -1 : currentpage }"/>
<c:set var="displaymin" value="${currentpage + displayrange > totalpage ?
	(1 > totalpage - (2*displayrange) ? 1 : totalpage - (2*displayrange)) :
	(1 > currentpage - displayrange ? 1 : currentpage - displayrange)
}"/>
<c:set var="displaymax" value="${currentpage - displayrange < 1 ?
	(totalpage < 1+ (2*displayrange) ? totalpage : 1+ (2*displayrange)) :
	(totalpage < currentpage + displayrange ? totalpage : currentpage + displayrange)
}"/>

<ul class="pagination">
	<li>
		<a href="<ex:linker target="/home" pagenb="1"/>" aria-label="First">
			<span aria-hidden="true">&lt;&lt;</span>
		</a>
	</li>
	<li>
		<a href="<ex:linker target="/home" pagenb="${previouspage}"/>" aria-label="Previous">
			<span aria-hidden="true">&lt;</span>
		</a>
	</li>
	<c:forEach var="i" begin="${displaymin}" end="${displaymax}">
		<li class=${i eq currentpage?"active":"" }>
			<a href="<ex:linker target="/home" pagenb="${i}"/>">${i}</a>
		</li>
	</c:forEach>
	<li>
		<a href="<ex:linker target="/home" pagenb="${nextpage}"/>" aria-label="Next">
			<span aria-hidden="true">&gt;</span>
		</a>
	</li>
	<li>
		<a href="<ex:linker target="/home" pagenb="${totalpage}"/>" aria-label="Last">
			<span aria-hidden="true">&gt;&gt;</span>
		</a>
	</li>
</ul>