var io = require('socket.io').listen(7000);

var words = new Array('송진우', '돌맹이', '나뭇가지', '충전기', '노트북')

var count = 0;
var roomCount = 0;

var peer;

function randomitem(words) {
  var ritem = words[Math.floor(Math.random()*words.length)];
  console.log(ritem);
  return ritem;
}

io.on('connection', client => {
    count++;
    console.log(count);

    if (count % 2 != 0) {
        peer = client;
        roomCount++;
    }
    client.join('room' + roomCount);
    
    client.to('room' + roomCount).on('ready', data => {
        console.log(roomCount + ' 방 ready 됨');
        var wordDatas = new Array();
        for(var i = 0; i < 5; i++) {
            wordDatas[i] = randomitem(words);
        }
        var roomName = 'room' + roomCount;

        if (count % 2 == 0) {
            peer.to(roomName).emit('start', true);
            client.to(roomName).emit('start', false);
        }

        client.to(roomName).on('roundStart', data => {
            client.to(roomName).emit('wordData', randomitem(words))
        });

        client.to(roomName).on('action', data => {
            client.to(roomName).emit('action', data);
        });

        client.to(roomName).on('pass', data => {
            client.to(roomName).on('pass')
        });
    });

    client.on('disconnect', data => {
        count--;
        console.log(count)
    })
});