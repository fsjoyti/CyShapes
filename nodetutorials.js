
var app = require('express')();
var http = require('http').Server(app);
var fs = require('fs');
//var io = require('socket.io')(http);
var io = require('socket.io');

var sql = require('mysql');
var querystring = require('querystring');


var databasecon = sql.createConnection({
    host:'mysql.cs.iastate.edu',
    user: 'dbu309grp43',
    password:'zGtT4R5s5fR',
    database :'db309grp43'
});


databasecon.connect(function(err){
    if(err){
        console.log('Error connecting to Db');
        return;
    }
    console.log('Connection established');
});
databasecon.query('SELECT * FROM Students',function(err,rows){
    if(err) throw err;

    console.log('Data received from Db:\n');
    console.log(rows);
});



app.get('/', function(req, res){
    if (req.method=='GET'&&req.url=='/'){
    res.writeHead(200,{"Content-Type":"text/html"});
    fs.createReadStream("./CyShapes.html").pipe(res);

    }






});


var listener = io.listen(http);
listener.sockets.on('connection', function(socket){
    console.log('a user connected');
    setInterval(function(){
        socket.emit('date', {'date': new Date()});}, 1000);
    socket.on('disconnect', function(){

        console.log('user disconnected');

    });
    socket.on('client_data', function(data){
        process.stdout.write(data.letter);
        console.log(data.letter);

    });

});
var socket = io.connect;




http.listen(3000, function(){
    console.log('listening on *:3000');
});