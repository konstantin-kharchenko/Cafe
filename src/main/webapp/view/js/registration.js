function load() {
    var repeat = document.getElementById('hidRepeat').value;
    console.log(repeat);
    if (repeat) {
        var name = document.getElementById('hiddenName').value;
        console.log(name);
        var surname = document.getElementById('hiddenSurname').value;
        var age = document.getElementById('hiddenAge').value;console.log(age);
        var email = document.getElementById('hiddenEmail').value;
        var phone = document.getElementById('hiddenPhone').value;
        var login = document.getElementById('hiddenLogin').value;
        var password = document.getElementById('hiddenPassword').value;
        var role = document.getElementById('hiddenRoles').value;
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
        }
        if (role === "administrator") {
            document.getElementById('radioRole2').checked = true;
        }
    }
}