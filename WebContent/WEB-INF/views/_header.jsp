<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
	<div style="float: left">
		<h1>Product Management Site</h1>
	</div>
	
	<div style="float: right; padding: 10px; text-align: right;">
	
		<!-- User stored in Session with Attribute: loginedUser -->
		Hello <b>${loginedUser.userName}</b>
		<br/>
		Search <input name = "search">
		
	</div>
</div>