<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                                     
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Explanation of Benefits</title>


<style>
table {
    border-collapse: collapse;
}
table, tr, td {
    border: 1px solid black;
   
}
table{
	width:100%
}
div.left{
	display:inline-block;
	width:70%;
	padding-bottom:1%;
	height:8%
}
div.right{
	display:inline-block;
	width:30%;
	text-align:right;
	padding-bottom:1%;
	height:8%
}
table#detailTable {
    border-collapse: none;
}
table#detailTable , tr , td {
    border: 1px solid black;
   
}
table#detailTable , td {
	 width:6%; word-wrap:break-word;
}
</style>
</head>
<body>
<div style="padding:2%;border-radius: 25px; border: 4px solid #000000;">
	<div style="font-size:.6em" id="header">
		<div style="width:10%:height:4%">
			<h1>ABC Health Insurance</h1>
		</div>
		<div style="float:left">
		<span id="compAddress1">An independent licensee of the Blue Cross and Blue Shield Association</span>
		<br/>
		<span id="compAddress2">ABC Blue Cross and Blue Shield is the trade name of ABC Health Insurance of MY, Inc.</span>
		<br/>
		<span id="compAddress3">Registered Marks Blue Cross and Blue Shield Association.</span>
		</div>
	</div>
	<div style="display:inline-block;width:100%" id="firstPart">
		<div id="clientDetail" style="display:inline-block;width:30%;height:50%;boarder:1px solid #000000;height:30%;float:left">
			<div style="align:center"><h3>&nbsp;</h3></div>
			<div style="border:1px solid #000000;">
				<div class="left">CHECK NUMBER:</div><div class="right">N/A</div>
			</div>
			<div style="border-left:1px solid #000000;border-right:1px solid #000000;border-bottom:1px solid #000000;">
				<div class="left">&nbsp;</div><div class="right">&nbsp;</div>
				<c:if test="${not empty eobHeader}">
					<div class="left">PATIENT:</div><div class="right"><c:out value="${eobHeader.patient}" /></div>
					<div class="left">PATIENT ACCOUNT #:</div><div class="right"><c:out value="${eobHeader.patientAccount}" /></div>
					<div class="left">INSURED ID:</div><div class="right"><c:out value="${eobHeader.insuredID}" /></div>
					<div class="left">PROVIDER:</div><div class="right"><c:out value="${eobHeader.providerId}" /></div>
					<div class="left">CLAIM #:</div><div class="right"><c:out value="${eobHeader.claimId}" /></div>
					<div class="left">PROVIDER PARTICIPATION STATUS:</div><div class="right"><c:out value="${eobHeader.providerStatus}" /></div>
					<div class="left">EOB DATE:</div><div class="right"><c:out value="${eobHeader.eobDate}" /></div>
					<div class="left">AMOUNT PROVIDER MAY BILL YOU,</div><div class="right">&nbsp;</div>
					<div class="left">IF NOT ALREADY PAID</div><div class="right"><c:out value="${eobHeader.amtByProvider}" /></div>
				</c:if>
				<c:if test="${empty eobHeader}">
					<div class="left">PATIENT:</div><div class="right">N/A</div>
					<div class="left">PATIENT ACCOUNT #:</div><div class="right">&nbsp;</div>
					<div class="left">INSURED ID:</div><div class="right">N/A</div>
					<div class="left">PROVIDER:</div><div class="right">eobHeader.</div>
					<div class="left">CLAIM #:</div><div class="right">N/A</div>
					<div class="left">PROVIDER PARTICIPATION STATUS:</div><div class="right">N/A</div>
					<div class="left">EOB DATE:</div><div class="right">N/A</div>
					<div class="left">AMOUNT PROVIDER MAY BILL YOU,</div><div class="right">&nbsp;</div>
					<div class="left">IF NOT ALREADY PAID</div><div class="right">100.00</div>
				</c:if>
				
			</div>
		</div>
		<div id="history" style="padding-left:2%;width:65%;display:inline-block;height:50%">
			<div style="text-align:center;width:100%"><h3>THIS IS NOT A BILL</h3></div>
			<div style="padding-top:1px;">
			<table>
				<tr ><td style="text-align:center" colspan="4">YOUR BENEFIT SNAPSHOT*</td></tr>
				<tr>
					<td style="width:50%">BENEFIT YEAR 2016</td>
					<td style="width:10% ;text-align:center">BENEFIT AMT</td>
					<td style="width:10% ;text-align:center">AMOUNT MET-</td>
					<td style="width:10% ;text-align:center">RMAINING<br/> BALANCE</td>
				</tr>
				<c:forEach items="${benefitList}" var="benefit">
				<tr>
					<td style="width:50%"><c:out value="${benefit.description}" /></td>
					<td style="width:10% ;text-align:center"><c:out value="${benefit.benefitAmt}" /></td>
					<td style="width:10% ;text-align:center"><c:out value="${benefit.amtYTD}" /></td>
					<td style="width:10% ;text-align:center"><c:out value="${benefit.balanceAmount}" /></td>
				</tr>
				</c:forEach>
			</table>
			</div>
		</div>
	</div>
	<br/><br/><br/>
	<div style="display:inline-block;width:100%" id="details">
	<table style="text-align:center;table-layout:fixed" id="#detailTable">
		<tr>
			<td>DATE(S) OF SERVICE</td>
			<td>CODES</td>
			<td>TYPE OF SERVICE</td>
			<td>CHARGE</td>
			<td>ALLOWABLE AMOUNT</td>
			<td>PROVIDER RESPONSIBILITY</td>
			<td>REASON CODE(S)</td>
			<td>DEDUCTIBLE</td>
			<td>COPAY/ COINSURANCE</td>
			<td>ADDITIONAL MEMBER RESPONSIBILITY</td>
			<td>REASON CODES</td>
			<td>AMOUNT PAID TO PROVIDER</td>
		</tr>
		<tr>
			<td><c:out value="${claim.serviceDate}"/></td>
			<td><c:out value="${claim.diagCode}"/></td>
			<td><c:out value="${claim.procedureCode}"/></td>
			<td><c:out value="${claim.chargedAmount}"/></td>
			<td><c:out value="${claim.approvedAmount}"/></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><c:out value="${claim.paitentLiability}"/></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><c:out value="${claim.finalApprovedAmount}"/></td>
		</tr>
	</table>
	</div>
	<div>
	<p>
	THIS IS AN EXPLAINATION OF THE CLAIMS PROCESSED BY ANTHEM FOR BENEFITS PROVIDED TO YOU. REASON CODES,<br/>
	WHEN APPLICABLE, ARE EXPLAINED AT THE BOTTOM OF THE LAST PAGE. IF YOU FILED MULTIPLE PROVIDER BILLS,<br/>
	THEY MAY BE PROCESSED SEPARATELY. CLAIMS FOR EMERGENCY CARE FROM A NON-NETWORK PROVIDER MAY BE<br/>
	APPROVED TO PAY MORE IF YOU RECEIVE A BILL FOR MORE THAN THE ALLOWED AMOUNT. CALL CUSTOMER SERVICE.<br/>
	<br/>
	<br/>
	IF YOU ARE COVERED BY MORE THAN ONE (1) BENEFIT PLAN, YOU SHOULD FILE ALL YOUR CLAIMS WITH EACH PLAN.<br/>
	THIS CLAIM MAY HAVE BEEN PAID AS IF ANTHEM WERE THE PRIMARY CARRIER. IF YOU HAVE COVERAGE WITH TWO OR<br/>
	MORE PLANS, THE PLANS COORDINATION OF BENEFITS RULES WILL BE USED TO DETERMINE HOW MUCH EACH PLAN<br/>
	PAYS. PLEASE CONTACT CUSTOMER SERVICE TO UPDATE YOUR OTHER PLAN INFORMATION.<br/>
	<br/>
	<br/>
	*CLAIMS ARE PROCESSED IN ORDER OF DATE RECEIVED, NOT NECESSARILY IN DATE OF SERVICE ORDER.<br/>
	</p>
	</div>
</div>
</body>
</html>