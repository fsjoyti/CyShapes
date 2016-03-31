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
var UUID = require('node-uuid');

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
var favicon = require('static-favicon');
var flash = require('connect-flash');
var mongooseadmin = require('mongooseadmin');
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
app.use('/admin',mongooseadmin());



require('./Routes/Routes.js')(app,passport);
require('./Controllers/admin.js')(app);

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

var Socket_List = {};
var Player_List = {};
var Player = function(id){
    var self = {
        x:250,
        y:250,
        id:id,
        number:""+ Math.floor(10 * Math.random()),
        pressingRight:false,
        pressingLeft:false,
        pressingUp:false,
        pressingDown:false,
        maxSpd:10
    }
    self.updatePosition = function(){
        if(self.pressingRight)
            self.x += self.maxSpd;
        if(self.pressingLeft)
            self.x -= self.maxSpd;
        if(self.pressingUp)
            self.y -= self.maxSpd;
        if(self.pressingDown)
            self.y += self.maxSpd;
    }

    return self;

}
var thisGameId;
io.on('connection', function(socket){
    console.log('a user connected');

    socket.id = Math.random();


    Socket_List[socket.id] = socket;
    var player = Player(socket.id);
    Player_List[socket.id] = player;
    //tell the player they connected, giving them their id
    socket.emit('onconnected', { id: socket.id } );

    //Useful to know when someone connects
    console.log('\t socket.io:: player ' + socket.id + ' connected');
    socket.on('hostCreateNewGame', function(data){
                console.log(data);
            thisGameId = ( Math.random() * 100000 ) | 0;
            socket.emit('newGameCreated', {gameId: thisGameId, mySocketId: socket.id});
           // console.log(""+'newGameCreated', {gameId: thisGameId, mySocketId: this.id});

            //Join the Room and wait for the players
            socket.join(thisGameId.toString());


    }
    );


    socket.on('hostRoomFull',function(data){

        /*
            var sock = this;
            var data = {
                mySocketId : sock.id,
                gameId : gameId
            };
        sockets.in(data.gameId).emit('beginNewGame',data);
        */
        console.log(""+data.gameId);

    }
    );
    socket.on('hostCountdownFinished',function(){
        console.log('Game Started.');

    });


    socket.on('disconnect', function(){
        console.log('user disconnected');
        console.log('\t socket.io:: client disconnected ' + socket.id );
        delete Socket_List[socket.id];
        delete Player_List[socket.id];
    });
    socket.on('keyPress',function(data){
        if(data.inputId === 'left')
            player.pressingLeft = data.state;
        else if(data.inputId === 'right')
            player.pressingRight = data.state;
        else if(data.inputId === 'up')
            player.pressingUp = data.state;
        else if(data.inputId === 'down')
            player.pressingDown = data.state;
    });
});



function hostPrepareNewGame(){
    var data = {
        mySocketId : socket.id,
        gameId : thisGameId
    };
    //console.log("All Players Present. Preparing game...");
    sockets.in(data.gameId).emit('beginNewGame', data);

}
function hostStartGame(){
    console.log('Game Started.');
}
setInterval(function(){
    var pack = [];
    for (var i in Player_List){
        var player = Player_List[i];
        player.x++;
        player.y++;
        pack.push({
            x:player.x,
            y:player.y,
            number:player.number
        });
        for (var i in Socket_List) {
            var socket = Socket_List[i];
            socket.emit('newPosition', pack);
        }
    }

},1000/25);

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
