$(document).ready(function () {
    //now date
    var date = new Date();
    //init
    $('#cnzz_stat_icon_5894820').hide();
    $('#copyright-year').text(date.getFullYear());
    changeTitle();

    setInterval(function () {
        changeTitle();
    }, 5000);
});

//change page title
function changeTitle() {
    //emoji
    var num = parseInt(Math.random() * 10);
    var emoji = ['>_<', '⊙﹏⊙', '→_→', 'O__O', 'π_π', '^_^', '(╯-╰)', '(*_* )', ' ( ⊙o⊙ )', '(￣︶￣)'];
    //change
    $('#title').text(emoji[num]);
}
