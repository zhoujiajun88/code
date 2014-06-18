/***
 * 2048 game common
 * @author zhoujiajun
 * @time 2014-06-16
 */

var pressKeyTime;

var x1, y1, x2, y2;

/**
 * 游戏初始化
 */
$(document).ready(function () {
    // now date
    var date = new Date();
    // init page title
    $('#cnzz_stat_icon_5894820').hide();
    // change Title
    setInterval(function () {
        changeTitle();
    }, 5000);
    // init game
    createChessboard();
    // pressKey time init
    pressKeyTime = new Date().getTime();
});

/**
 * 上下左右按键兼听
 */
$(document).keydown(function (event) {
    if (new Date().getTime() - pressKeyTime > 600) {
        if (event.keyCode == 37) {
            event.preventDefault();
            pressKey('left');
        } else if (event.keyCode == 38) {
            event.preventDefault();
            pressKey('up');
        }
        else if (event.keyCode == 39) {
            event.preventDefault();
            pressKey('right');
        }
        else if (event.keyCode == 40) {
            event.preventDefault();
            pressKey('down');
        }
        pressKeyTime = new Date().getTime();
    } else {
        event.preventDefault();
    }
});

/**
 * 滑动事件兼听
 */
document.addEventListener('touchstart', function (event) {
    x1 = event.touches[0].pageX;
    y1 = event.touches[0].pageY;
});

document.addEventListener('touchend', function (event) {
    x2 = event.changedTouches[0].pageX;
    y2 = event.changedTouches[0].pageY;

    var x = x2 - x1;
    var y = y2 - y1;

    if (Math.abs(x) < 0.3 * allWidth && Math.abs(y) < 0.3 * allWidth) {
        return;
    }

    if (Math.abs(x) >= Math.abs(y)) {
        if (x > 0) {
            pressKey('right');
        } else {
            pressKey('left');
        }
    } else {
        if (y > 0) {
            pressKey('down');
        }
        else {
            pressKey('up');
        }
    }
});
document.addEventListener('touchmove', function (event) {
    event.preventDefault();
});





