<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="${abs}/view/css/registration.css" type="text/css"/>
    <script src="${abs}/view/js/registration.js"></script>
</head>
<body bgcolor="#90ee90" onload="load()">
<c:import url="${abs}/view/pages/header/main.jsp"/>
<form action="${abs}/registration" method="post">
    <input type="hidden" id="hiddenOldPage" name="hidoldPage" value="${old_page}"/>
    <input type="hidden" id="hiddenRoles" name="hidroles" value="${user.get("role")}"/>
    <input type="hidden" id="hiddenName" name="hidname" value="${user.get("name")}"/>
    <input type="hidden" id="hiddenSurname" name="hidsurname" value="${user.get("surname")}"/>
    <input type="hidden" id="hiddenAge" name="hidage" value="${user.get("age")}"/>
    <input type="hidden" id="hiddenEmail" name="hidemail" value="${user.get("email")}"/>
    <input type="hidden" id="hiddenPhone" name="hidphone" value="${user.get("phone_number")}"/>
    <input type="hidden" id="hiddenStatus" name="hidstatus" value="${user.get("status")}"/>
    <input type="hidden" id="hiddenPayment" name="hidpayment" value="${user.get("payment_type")}"/>
    <input type="hidden" id="hiddenLogin" name="hidlogin" value="${user.get("login")}"/>
    <input type="hidden" id="hiddenPassword" name="hidpassword" value="${user.get("password")}"/>
    <input type="hidden" id="hiddenExperience" name="hidexperience" value="${user.get("experience")}"/>
    <br/><br/>
    <h1>REGISTRATION</h1><br/>
    <h2>
        <p>
            CLIENT
            <input type="radio" checked id="radioRole1" name="role" value="client" onclick="showDiv()"/>
            ADMINISTRATOR
            <input type="radio" id="radioRole2" name="role" value="administrator" onclick="hiddenDiv()"/>
        <p/>
    </h2>
    <input class="input" required type="text" name="name" value="${user.get("name")}" placeholder="name" minlength="2"
           maxlength="10"/><label id="labelName"></label>
    <input class="input" required type="text" name="surname" value="${user.get("surname")}" placeholder="surname"
           minlength="2" maxlength="10"/><label id="labelSurname"></label>
    <input class="input" required type="email" name="email" value="${user.get("email")}" placeholder="email"/><label
        id="labelEmail"></label>
    <input class="input" required type="text" name="phone_number" value="${user.get("phone_number")}"
           placeholder="phone number"/><label id="labelPhone"></label>
    <input class="input" required type="number" name="age" value="${user.get("age")}" placeholder="age" min="12"
           onblur="ageValidate(this.value)"/><label id="labelAge"></label>
    <div style="display:block;" id="payment_type">
        <h2>
            cash
            <input type="radio" checked id="radioCash" name="payment_type" value="cash"/>
            client account
            <input type="radio" id="radioAccount" name="payment_type" value="client_account"/>
        </h2>
    </div>
    <input style="display: none" class="input" id="inputExperience" type="number" name="experience"
           value="${user.get("experience")}"
           placeholder="experience" minlength="2" maxlength="20" onblur="experienceValidate(this.value)"/><label
        id="labelExperience"></label>
    <input class="input" id="login" required type="text" name="login" value="${user.get("login")}" placeholder="Login"
           minlength="2" maxlength="20"/><label id="labelLogin"></label>
    <input class="input" required type="password" name="password" value="${user.get("password")}" placeholder="Password"
           minlength="4"
           maxlength="12"/><label id="labelPassword"></label>
    <br/><br/><label>${exception_msg}</label><br/><br/>
    <button name="command" id="button" value="registration">Registration</button>
    <a href="${abs}/view/pages/common/login.jsp">BACK</a></br>
    <br/>
</form>
</body>
</html>
