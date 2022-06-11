function load() {
    var repeat = document.getElementById('hidRepeat').value;
    console.log(repeat);
    if (repeat) {
        var name = document.getElementById('hiddenName').value;
        console.log(name);
        var date = document.getElementById('hiddenDate').value;
        var payment_type = document.getElementById('hiddenPaymentType').value;
        console.log(payment_type);
        if (name === "") {
            lable = document.getElementById('labelName');
            lable.innerHTML = 'invalid';
        }
        if (date === "") {
            lable = document.getElementById('labelDate');
            lable.innerHTML = 'invalid';
        }
        if (payment_type === "CASH") {
            document.getElementById('radioRole1').checked = true;
        }
        if (payment_type === "CLIENT_ACCOUNT") {
            document.getElementById('radioRole2').checked = true;
        }
    }
}