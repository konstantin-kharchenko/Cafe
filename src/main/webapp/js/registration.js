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
        console.log(password.length);
        if (login === "") {
            lable = document.getElementById('labelLogin');
            lable.innerHTML = 'invalid';
        }
        if (password === "") {
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