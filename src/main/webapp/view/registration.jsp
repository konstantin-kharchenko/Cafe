<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="../css/registration.css" type="text/css"/>
    <script>
        function showDiv() {
            var div = document.getElementById('payment_type');
            div.style.display = 'block';
            var input = document.getElementById('inputExperience');
            input.style.display = 'none';
        }

        function hiddenDiv() {
            var div = document.getElementById('payment_type');
            div.style.display = 'none';
            var input = document.getElementById('inputExperience');
            input.style.display = 'block';
        }

        function ageValidate(age) {
            var submitButton = document.getElementById('button');
            var matcher = false
            if (age < 12) {
                matcher = true
            }
            submitButton.disabled = matcher;
            var lable;
            lable = document.getElementById('labelAge');
            if (matcher === true) {
                lable.innerHTML = 'age must be more 12';
            } else {
                lable.innerHTML = '';
            }
        }

        function experienceValidate(experience) {
            var submitButton = document.getElementById('button');
            var matcher = false
            if (experience < 0) {
                matcher = true
            }
            submitButton.disabled = matcher;
            var lable;
            lable = document.getElementById('information3');
            if (matcher === true) {
                lable.innerHTML = 'experience must be more 0';
            } else {
                lable.innerHTML = '';
            }
        }

        function load() {
            var oldPage = document.getElementById('hiddenOldPage').value;
            if (oldPage === "view/registration.jsp") {
                var name = document.getElementById('hiddenName').value;
                var surname = document.getElementById('hiddenSurname').value;
                var age = document.getElementById('hiddenAge').value;
                var email = document.getElementById('hiddenEmail').value;
                var phone = document.getElementById('hiddenPhone').value;
                var login = document.getElementById('hiddenLogin').value;
                var password = document.getElementById('hiddenPassword').value;
                var experience = document.getElementById('hiddenExperience').value;
                var role = document.getElementById('hiddenRoles').value;
                var payment = document.getElementById('hiddenPayment').value;
                if (login === "") {
                    lable = document.getElementById('labelLogin');
                    lable.innerHTML = 'invalid';
                }
                if (password === "") ;
                {
                    lable = document.getElementById('labelPassword');
                    lable.innerHTML = 'invalid';
                }
                if (name === "") {
                    lable = document.getElementById('labelName');
                    lable.innerHTML = 'invalid';
                }
                if (surname === "") {
                    lable = document.getElementById('labelSurname');
                    lable.innerHTML = 'invalid';
                }
                if (age === "") {
                    lable = document.getElementById('labelAge');
                    lable.innerHTML = 'invalid';
                }
                if (email === "") {
                    lable = document.getElementById('labelEmail');
                    lable.innerHTML = 'invalid';
                }

                if (phone === "") {
                    lable = document.getElementById('labelPhone');
                    lable.innerHTML = 'invalid';
                }
                if (role === "client") {
                    document.getElementById('radioRole1').checked = true;
                    if (payment === "cash") {
                        document.getElementById('radioCash').checked = true;
                    } else {
                        document.getElementById('radioAccount').checked = true;
                    }
                }
                if (role === "administrator") {
                    document.getElementById('radioRole2').checked = true;
                    hiddenDiv();
                    if (experience === "") {
                        lable = document.getElementById('labelExperience');
                        lable.innerHTML = 'invalid';
                    }
                }
            }
        }
    </script>
</head>
<body bgcolor="#90ee90" onload="load()">
<c:import url="pages/header/main.jsp"/>
<form action="../controller" method="post">
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
    <input class="input" required type="password" name="password" placeholder="Password"
           minlength="4"
           maxlength="12"/><label id="labelPassword"></label>
    <br/><br/><label>${exception_msg}</label><br/><br/>
    <button name="command" id="button" value="registration">Registration</button>
    <br/>
</form>
</body>
</html>
