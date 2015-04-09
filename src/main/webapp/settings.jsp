<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<div class="container">
		<form action="${pageContext.request.contextPath}/settings" method="post">
			<p class="small">Select the calendars you are interested in viewing:</p>
			<input type="checkbox" name="calendar" id="academic">Academic Calendar
			<br>
			<input type="checkbox" name="calendar" id="ccee">CCEE Student Organization Calendar
			<br>
			<input type="checkbox" name="calendar" id="csc">CSC Department Calendar
			<br>
			<input type="checkbox" name="calendar" id="physics">Physics Department Calendar
			<br>
			<input type="submit" name="Save" value="Save">
		</form>
	</div>

</t:layout>

<script>
	function loadSavedSettings() {
		var a = document.getElementById("academic");
		var b = document.getElementById("ccee");
		var c = document.getElementById("csc");
		var d = document.getElementById("physics");
		String list = request.getAttribute("savedSettings").toString();
		if (list.indexOf("ncsu.edu_507c8794r25bnebhjrrh3i5c4s@group.calendar.google.com") > -1) {
			a.checked = true;
		}
		if (list.indexOf("ncsu.edu_iv41gou4edva6l3sejfg9mjo2k@group.calendar.google.com") > -1) {
			b.checked = true;
		}
		if (list.indexOf("ncsu.edu_hpasl5cmtenq7biv0omve1nvq8@group.calendar.google.com") > -1) {
			c.checked = true;
		}
		if (list.indexOf("ncsu.edu_vd4gv8ter4klr9sa6efm5vmsq0@group.calendar.google.com") > -1) {
			d.checked = true;
		}
	}
</script>
<script>
	window.onload = loadSavedSettings;
</script>
