/**
 * Created by Anastasia on 2/19/2016.
 */


//var app = require('express')();
//var http = require('http').Server(app);
var fs = require('fs');
//var io = require('socket.io')(http);
var io = require('socket.io');
v
var path = require('path');
var bodyParser = require('body-parser');
var urlencodedParser = bodyParser.urlencoded({ extended: false });

var express = require('express');
var app = express();
    var sql = require('mysql');
    var querystring = require('querystring');


    var databasecon = sql.createConnection({
        host: 'mysql.cs.iastate.edu',
        user: 'dbu309grp43',
        password: 'zGtT4R5s5fR',
        database: 'db309grp43'
    });


    databasecon.connect(function (err) {
        if (err) {
            console.log('Error connecting to Db');
            return;
        }
        console.log('Connection established');
    });
    databasecon.query('SELECT * FROM Students', function (err, rows) {
        if (err) throw err;

        console.log('Data received from Db:\n');
        //console.log(rows);
        fs.writeFile('table.json', JSON.stringify(rows), function (err) {
            if (err) throw err;
            console.log('Saved!');
        });


    });

app.get("/goodbye", function (req, res) {
    //send "Goodbye World" to the client as html
    res.send("Goodbye World!");
});


app.get('/', function(req, res){
    if (req.method=='GET'&&req.url=='/'){
        /*

    res.writeHead(200,{"Content-Type":"text/html"});

    fs.createReadStream("./CyShapes.html").pipe(res);
        var contents = fs.readFileSync("table.json");
        var jsonContent = JSON.parse(contents);

*/


        res.writeHead(200,{"Content-Type":"text/html"});

        fs.createReadStream("./CyShapes.html").pipe(res);



    }








});

var usersFilePath = path.join(__dirname, 'table.json');

app.get('/users', function (req, res) {

    // Prepare output in JSON format
    var readable = fs.createReadStream(usersFilePath);
    readable.pipe(res);



});
app.use(express.static('public'));
app.get('/LoginPage', function (req, res) {
    res.sendFile( __dirname + "/" + "LoginPage.html" );
})

app.get('/process_get', urlencodedParser, function (req, res) {


    // Prepare output in JSON format
    var response = {
        username:req.body.user_name,
        password:req.body.password
    };
    console.log(response);
    res.end(JSON.stringify(response));
})









/*

var listener = io.listen(http);
listener.sockets.on('connection', function(socket,res) {
    console.log('a user connected');

    socket.on('disconnect', function(){

        console.log('user disconnected');

    });


    socket.on('client_data', function(data){
        process.stdout.write(data.letter);
        console.log(data.letter);

    });
    socket.on('CH01', function (from, msg) {
        console.log('MSG', from, ' saying ', msg);
    });


});
var socket = io.connect;


*/
/*
http.listen(3000, function(){
    console.log('listening on *:3000');
});
    */
var server = app.listen(3000, function (socket) {

    var host = server.address().address
    var port = server.address().port

    console.log("Example app listening at http://%s:%s", host, port);






});
