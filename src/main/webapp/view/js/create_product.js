function load() {
    var repeat = document.getElementById('hidRepeat').value;
    console.log(repeat);
    if (repeat) {
        var name = document.getElementById('hiddenName').value;
        var date = document.getElementById('hiddenDate').value;
        var price = document.getElementById('hiddenPrice').value;
        if (name === "") {
            lable = document.getElementById('labelName');
            lable.innerHTML = 'invalid';
        }
        if (date === "") {
            lable = document.getElementById('labelDate');
            lable.innerHTML = 'invalid';
        }
        if (price === "") {
            lable = document.getElementById('labelPrice');
            lable.innerHTML = 'invalid';
        }
    }
}