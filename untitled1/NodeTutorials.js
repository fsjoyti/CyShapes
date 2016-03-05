/**
 * Created by Anastasia on 2/19/2016.
 */


//var app = require('express')();
//var http = require('http').Server(app);
var fs = require('fs');
var logger = require ('morgan');
//var io = require('socket.io')(http);
var io = require('socket.io');

var path = require('path');
var bodyParser = require('body-parser');
var cookieparser = require('cookie-parser');
var session = require('express-session');

var urlencodedParser = bodyParser.urlencoded({ extended: false });
//var LocalStrategy   = require('passport-local').Strategy;
var TwiiterStrategy = require('passport-twitter');
var GoogleStrategy = require('passport-google');
var FacebookStrategy = require('passport-facebook').Strategy;
var passport = require('passport');
var config = require('./config.js');
var configAuth = require('./auth.js');
var mongoose = require('mongoose');
var MongoStore = require('connect-mongo')(session);
var configDB = require('./Config/Database.js');
mongoose.connect('mongodb://localhost:27017');

var flash = require('connect-flash');
var express = require('express');
var app = express();
app.use(logger('dev'));
require('./Config/passport')(passport);

app.use(cookieparser());
app.use(urlencodedParser);
app.use(bodyParser.urlencoded({extended:true}));
app.set('view engine', 'ejs');
app.use (session({secret:'supernova',saveUninitialized:true,resave:true,store:new MongoStore({mongooseConnection:mongoose.connection})}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());


require('./Routes/Routes.js')(app,passport);



/*
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
    databasecon.query('SELECT * FROM Users', function (err, rows) {
        if (err) throw err;

        console.log('Data received from Db:\n');
        //console.log(rows);
        fs.writeFile('table.json', JSON.stringify(rows), function (err) {
            if (err) throw err;
            console.log('Saved!');
        });


    });
/*
module.exports = function(passport){

    passport.serializeUser(function (user, done) {
        done(null,user);
    });
    passport.deserializeUser(function(username, done) {
        databasecon .query("select * from Users where username = "+username,function(err,rows){
            done(err, rows[0]);
        });
    });
};

*/


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
});


app.get('/process_get', urlencodedParser, function (req, res) {


    // Prepare output in JSON format
    var response = {

        username:req.query.username,
        password:req.query.password

    };
    console.log(response);
    res.end(JSON.stringify(response));

});
/*
passport.use(new LocalStrategy(
    function(username, password, done) {
        User.findOne({ username: username }, function(err, user) {
            if (err) { return done(err); }
            if (!user) {
                return done(null, false, { message: 'Incorrect username.' });
            }
            if (!user.validPassword(password)) {
                return done(null, false, { message: 'Incorrect password.' });
            }
            return done(null, user);
        });
    }
));

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

    var host = server.address().address;
    var port = server.address().port;

    console.log("Example app listening at http://%s:%s", host, port);






});
