var io = require('socket.io').listen(7000);

var words = new Array('a', 'as', 'asd', '1', '2')

var count = 0;
var roundClientCount = 0;
var round = 0;

var solver;
var drawer;

function randomitem(words) {
  var ritem = words[Math.floor(Math.random()*words.length)];
  return ritem;
}

io.on('connection', client => {
    count++;
    console.log(count + ' 명 입장');

    var wordDatas = new Array();

    for(var i = 0; i < 6; i++) {
        wordDatas[i] = randomitem(words);
    }

    if (count % 2 != 0) drawer = client;
    else solver = client;

    client.on('ready', data => {
        if (count % 2 == 0) {
            drawer.emit('start', true);
            solver.emit('start', false);
        }
    });

    client.on('roundStart', data => {
        roundClientCount++;
        console.log(roundClientCount + ' 번 준비완료');
        if (roundClientCount % 2 == 0) {
            round++;
            drawer.emit('wordData', wordDatas[round]);
            solver.emit('wordData', wordDatas[round]);
        }
    });

    client.on('action', data => {
        solver.emit('action', data);
    });

    client.on('pass', data => {
        console.log('pass');
        io.emit('pass');
        var flag = drawer;
        drawer = solver;
        solver = flag;
    });

    client.on('disconnect', data => {
        count--;
        if (count == 0) {
            roundClientCount = 0;
            round = 0;

            solver = null;
            drawer = null;
            console.log("초기화됨");
        }
        console.log(count);
    })
});