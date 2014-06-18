/***
 * 2048 game common
 * @author zhoujiajun
 * @time 2014-06-16
 */


/**
 * 动态表情
 */
function changeTitle() {
    //emoji
    var num = parseInt(Math.random() * 10);
    var emoji = ['>_<', '⊙﹏⊙', '→_→', 'O__O', 'π_π', '^_^', '(╯-╰)', '(*_* )', ' ( ⊙o⊙ )', '(￣︶￣)'];
    //change
    $('#title').text(emoji[num]);
}


/*
 * =======游戏相关=======
 */
// 设置页面变量
var allWidth = window.screen.width - 10;
if (allWidth > 600) {
    allWidth = 600;
}
var gridSide = allWidth / 4; //格子边长
var gridPadding = allWidth / 60; //格子内边距
var numberBoxSide = allWidth / 4 - gridPadding * 2; //内棋子边长
// 初始化游戏数据
var data = new Array();
for (var i = 0; i < 4; i++) {
    data[i] = new Array();
    for (var j = 0; j < 4; j++) {
        data[i][j] = 0;
    }
}
// 文字显示
var numberText = ['0', '2', '4', '8', '16', '32', '64', '128', '256', '512', '1024', '2048', '4096'];
// 数字背景色
var numberBoxColor = ['#bdaea0', '#eee4da', '#ede1c8', '#efb478', '#ed8c4e', '#fc7957', '#ea572d', '#fce061', '#f4d03b', '#fdcd05', '#00e415', '#01870d', '#01870d', '#01870d'];
// 数字颜色
var numberColor = ['#000000', '#000000', '#000000', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff'];


/**
 * 取得棋子位置
 * @param i
 * @returns {number}
 */
function getNumberBoxTop(i) {
    return i * gridSide + gridPadding;
}
/**
 * 取得棋子位置
 * @param j
 * @returns {number}
 */
function getNumberBoxLeft(j) {
    return j * gridSide + gridPadding;
}
/**
 * 取得棋子颜色
 * @param number
 * @returns {string}
 */
function getNumberBoxColor(number) {
    var i = sqrt(number);
    if (numberBoxColor.length - 1 >= i) {
        return  numberBoxColor[i];
    } else {
        return numberBoxColor[numberBoxColor.length - 1];
    }
}
/**
 * 取得棋子文字颜色
 * @param number
 * @returns {string}
 */
function getNumberColor(number) {
    var i = sqrt(number);
    if (numberColor.length - 1 >= i) {
        return  numberColor[i];
    } else {
        return numberColor[numberColor.length - 1];
    }
}
/**
 * 取得棋子文字
 * @param number
 * @returns {string}
 */
function getNumberText(number) {
    var i = sqrt(number);
    if (numberText.length - 1 >= i) {
        return  numberText[i];
    } else {
        return number;
    }
}
/**
 * 统计有几个空格
 */
function countNumber() {
    var count = 0;
    for (var i = 0; i < 4; i++) {
        for (var j = 0; j < 4; j++) {
            if (data[i][j] == 0) {
                count++;
            }
        }
    }
    return count;
}
/**
 * 游戏是否结束
 */
function isGameOver() {
    if (countNumber() > 0) {
        return false;
    }
    if (canMove('left') || canMove('right') || canMove('up') || canMove('down')) {
        return false;
    }
    return true;
}
/**
 * 刷新显示数字框
 */
function refreshNumber() {
    $('.numberBox').remove();
    for (var i = 0; i < 4; i++) {
        for (var j = 0; j < 4; j++) {
            var radius = allWidth * 0.02;

            //背景框
            var backgroundBox = '<div class="numberBox" ' + 'style="width:' + numberBoxSide + 'px;height:' + numberBoxSide +
                'px;top:' + getNumberBoxTop(i) + 'px;left:' + getNumberBoxLeft(j) +
                'px;-moz-border-radius: ' + radius + 'px;-webkit-border-radius: ' +
                radius + 'px;border-radius: ' + radius + 'px;background-color:' + getNumberBoxColor(0) + '">' + '</div>';
            $('#game_2048').append(backgroundBox);

            //数字框
            var numberBox = '<div id="numberBox-' + i + '-' + j + '" class="numberBox" ' +
                'style="line-height:' + numberBoxSide + 'px;top:' +
                getNumberBoxTop(i) + 'px;left:' + getNumberBoxLeft(j) +
                'px;-moz-border-radius: ' + radius + 'px;-webkit-border-radius: ' +
                radius + 'px;border-radius: ' + radius + 'px;">' +
                '<span id="numberBoxNumber-' + i + '-' + j + '"></span></div>';
            $('#game_2048').append(numberBox);

            //文字相关设置
            var num = data[i][j];
            $('#numberBox-' + i + '-' + j).css('background-color', getNumberBoxColor(num));
            if (num > 0) {
                $('#numberBox-' + i + '-' + j).css('color', getNumberColor(num));
                var snum = getNumberText(num).toString();
                if (snum.length == 3) {
                    $('#numberBox-' + i + '-' + j).css('font-size', numberBoxSide * 0.4 + "px");
                } else if (snum.length == 2) {
                    $('#numberBox-' + i + '-' + j).css('font-size', numberBoxSide * 0.5 + "px");
                } else if (snum.length == 1) {
                    $('#numberBox-' + i + '-' + j).css('font-size', numberBoxSide * 0.7 + "px");
                } else {
                    $('#numberBox-' + i + '-' + j).css('font-size', numberBoxSide * 0.3 + "px");
                }
                $('#numberBoxNumber-' + i + '-' + j).text(snum);
                $('#numberBox-' + i + '-' + j).css('width', numberBoxSide);
                $('#numberBox-' + i + '-' + j).css('height', numberBoxSide);
            } else {
                $('#numberBoxNumber-' + i + '-' + j).text();
                $('#numberBox-' + i + '-' + j).css('width', 0);
                $('#numberBox-' + i + '-' + j).css('height', 0);
            }
        }
    }
}
/**
 * 在随机位置生成一个数字
 */
function generatePositionNumber() {
    if (!isGameOver()) {
        var num = parseInt(Math.random() * 3) > 1 ? 4 : 2;
        var count = countNumber();

        var position = parseInt(Math.random() * count);
        var temp = 0;

        refresh:
            for (var i = 0; i < 4; i++) {
                for (var j = 0; j < 4; j++) {
                    if (data[i][j] == 0) {
                        if (temp == position) {
                            data[i][j] = num;
                            $('#numberBox-' + i + '-' + j).animate({'width': numberBoxSide * 1.1, 'height': numberBoxSide * 1.1}, 100);
                            $('#numberBox-' + i + '-' + j).animate({'width': numberBoxSide, 'height': numberBoxSide}, 20);
                            break refresh;
                        }
                        temp++;
                    }
                }
            }

        setTimeout("refreshNumber()", 120);
    } else {
        alert('game over!得分：' + $('#score').text());
    }
}
/**
 * 初始化棋盘
 */
function createChessboard() {
    $('#game_2048').css('width', allWidth + 'px');
    $('#game_2048').css('height', allWidth + 'px');
    $('#score').text(0);
    generatePositionNumber();
    generatePositionNumber();
}
/**
 * 累加分数
 * @param score
 */
function addScore(score) {
    var nowScore = parseInt($('#score').text());
    $('#score').text(score + nowScore);
}
/**
 * 按键操作
 * @param op
 */
function pressKey(op) {
    if (canMove(op)) {
        eval(op + "()");
        setTimeout("generatePositionNumber()", 450);
    }
}
/**
 * 判断能否移动
 * @param redict 方向
 * @returns {boolean}
 */
function canMove(redict) {
    if (redict == 'left') {
        for (var i = 0; i < 4; i++) {
            for (var j = 1; j < 4; j++) {
                if (data[i][j] != 0 && (data[i][j - 1] == 0 || data[i][j - 1] == data[i][j])) {
                    return true;
                }
            }
        }
    } else if (redict == 'right') {
        for (var i = 0; i < 4; i++) {
            for (var j = 2; j >= 0; j--) {
                if (data[i][j] != 0 && (data[i][j + 1] == 0 || data[i][j + 1] == data[i][j])) {
                    return true;
                }
            }
        }
    } else if (redict == 'up') {
        for (var j = 0; j < 4; j++) {
            for (var i = 1; i < 4; i++) {
                if (data[i][j] != 0 && (data[i - 1][j] == 0 || data[i - 1][j] == data[i][j])) {
                    return true;
                }
            }
        }
    } else if (redict == 'down') {
        for (var j = 0; j < 4; j++) {
            for (var i = 2; i >= 0; i--) {
                if (data[i][j] != 0 && (data[i + 1][j] == 0 || data[i + 1][j] == data[i][j])) {
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * 是否有障碍物
 * @param redict 方向
 * @param line 行号列号
 * @param start 起始
 * @param end 结束
 * @returns {boolean}
 */
function noObstacle(redict, line, start, end) {
    if (redict == 'left') {
        for (var k = start + 1; k < end; k++) {
            if (data[line][k] != 0) {
                return false;
            }
        }
    } else if (redict == 'right') {
        for (var k = start - 1; k > end; k--) {
            if (data[line][k] != 0) {
                return false;
            }
        }
    } else if (redict == 'up') {
        for (var k = start + 1; k < end; k++) {
            if (data[k][line] != 0) {
                return false;
            }
        }
    } else if (redict == 'down') {
        for (var k = start - 1; k > end; k--) {
            if (data[k][line] != 0) {
                return false;
            }
        }
    }
    return true;
}
/*
 向左移动
 */
function left() {
    for (var i = 0; i < 4; i++) {
        var status = [true, true, true, true];
        for (var j = 1; j < 4; j++) {
            if (data[i][j] != 0) {
                for (var k = 0; k < j; k++) {
                    if (data[i][k] == data[i][j] && noObstacle('left', i, k, j) && status[k]) {
                        status[k] = false;
                        data[i][k] *= 2;
                        data[i][j] = 0;
                        addScore(data[i][k]);
                        $('#numberBox-' + i + '-' + j).animate({left: getNumberBoxLeft(k)}, 400);
                        continue;
                    } else if (data[i][k] == 0 && noObstacle('left', i, k, j)) {
                        data[i][k] = data[i][j];
                        data[i][j] = 0;
                        $('#numberBox-' + i + '-' + j).animate({left: getNumberBoxLeft(k)}, 400);
                        continue;
                    }
                }
            }
        }
    }
    setTimeout("refreshNumber()", 400);
}

/*
 向右移动
 */
function right() {
    for (var i = 0; i < 4; i++) {
        var status = [true, true, true, true];
        $('#numberBox-' + i + '-' + 3).css('z-index', 17);
        $('#numberBox-' + i + '-' + 2).css('z-index', 18);
        $('#numberBox-' + i + '-' + 1).css('z-index', 19);
        $('#numberBox-' + i + '-' + 0).css('z-index', 20);
        for (var j = 2; j >= 0; j--) {
            if (data[i][j] != 0) {
                for (var k = 3; k > j; k--) {
                    if (data[i][k] == data[i][j] && noObstacle('right', i, k, j) && status[k]) {
                        status[k] = false;
                        data[i][k] *= 2;
                        data[i][j] = 0;
                        addScore(data[i][k]);
                        $('#numberBox-' + i + '-' + j).animate({left: getNumberBoxLeft(k)}, 400);
                        continue;
                    } else if (data[i][k] == 0 && noObstacle('right', i, k, j)) {
                        data[i][k] = data[i][j];
                        data[i][j] = 0;
                        $('#numberBox-' + i + '-' + j).animate({left: getNumberBoxLeft(k)}, 400);
                        continue;
                    }
                }
            }
        }
    }
    setTimeout("refreshNumber()", 400);
}

/*
 * 向上移动
 */
function up() {
    for (var j = 0; j < 4; j++) {
        var status = [true, true, true, true];
        for (var i = 1; i < 4; i++) {
            if (data[i][j] != 0) {
                for (var k = 0; k < i; k++) {
                    if (data[k][j] == data[i][j] && noObstacle('up', j, k, i) && status[k]) {
                        status[k] = false;
                        data[k][j] *= 2;
                        data[i][j] = 0;
                        addScore(data[k][j]);
                        $('#numberBox-' + i + '-' + j).animate({top: getNumberBoxTop(k)}, 400);
                        continue;
                    } else if (data[k][j] == 0 && noObstacle('up', j, k, i)) {
                        data[k][j] = data[i][j];
                        data[i][j] = 0;
                        $('#numberBox-' + i + '-' + j).animate({top: getNumberBoxLeft(k)}, 400);
                        continue;
                    }
                }
            }
        }
    }
    setTimeout("refreshNumber()", 400);
}

/*
 * 向下移动
 */
function down() {
    for (var j = 0; j < 4; j++) {
        var status = [true, true, true, true];
        $('#numberBox-3-' + j).css('z-index', 17);
        $('#numberBox-2-' + j).css('z-index', 18);
        $('#numberBox-1-' + j).css('z-index', 19);
        $('#numberBox-0-' + j).css('z-index', 20);
        for (var i = 2; i >= 0; i--) {
            if (data[i][j] != 0) {
                for (var k = 3; k > i; k--) {
                    if (data[k][j] == data[i][j] && noObstacle('down', j, k, i) && status[k]) {
                        status[k] = false;
                        data[k][j] *= 2;
                        data[i][j] = 0;
                        addScore(data[k][j]);
                        $('#numberBox-' + i + '-' + j).animate({top: getNumberBoxTop(k)}, 400);
                        continue;
                    } else if (data[k][j] == 0 && noObstacle('down', j, k, i)) {
                        data[k][j] = data[i][j];
                        data[i][j] = 0;
                        $('#numberBox-' + i + '-' + j).animate({top: getNumberBoxTop(k)}, 400);
                        continue;
                    }
                }
            }
        }
    }
    setTimeout("refreshNumber()", 400);
}
/*
 计算2的开方
 */
function sqrt(number) {
    if (number == 0) {
        return  0;
    }
    var i = 0;
    for (; number != 1; i++) {
        number = number / 2;
    }
    return i;
}
