/**
 * Created by Anastasia on 2/19/2016.
 *
 * Updated by Andrew on 3/7/2016.
 */
var app = require('express')();
var http = require('http').Server(app);
var fs = require('fs');
var logger = require ('morgan');
var io = require('socket.io')(http);

var path = require('path');
var bodyParser = require('body-parser');
var cookieparser = require('cookie-parser');
var session = require('express-session');

var urlencodedParser = bodyParser.urlencoded({ extended: false });
var passport = require('passport');
var config = require('./config.js');
var mongoose = require('mongoose');
var MongoStore = require('connect-mongo')(session);
mongoose.connect('mongodb://localhost:27017');

var flash = require('connect-flash');
app.use(logger('dev'));
require('./Config/passport')(passport);

app.use(cookieparser());
app.use(urlencodedParser);
app.use(bodyParser.urlencoded({extended:true}));
app.set('view engine', 'ejs');
app.use(session({secret:'supernova',saveUninitialized:true,resave:true,store:new MongoStore({mongooseConnection:mongoose.connection})}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());


require('./Routes/Routes.js')(app,passport);

app.get('/process_get', urlencodedParser, function (req, res) {
    // Prepare output in JSON format
    var response = {

        username:req.query.username,
        password:req.query.password

    };
    console.log(response);
    res.end(JSON.stringify(response));

});

app.get('/chat', function(req, res){
    res.render('chat.ejs');
});

io.on('connection', function(socket){
    console.log('a user connected');
    socket.on('disconnect', function(){
        console.log('user disconnected');
    });
});

io.on('connection', function(socket){
    socket.on('chat message', function(msg){
        console.log('message: ' + msg);
    });
});

io.on('connection', function(socket){
    socket.on('chat message', function(msg){
        io.emit('chat message', msg);
    });
});

http.listen(3000,function (socket) {
    console.log("Example app listening at http://localhost:3000");
});

// For whatever reason the code below didn't want to play nicely with socket.io, so I commented it out

//var server = app.listen(3000, function (socket) {
//    var host = server.address().address;
//    var port = server.address().port;
//    console.log("Example app listening at http://%s:%s", host, port);
//});