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
var PlayerDatabase = require('./Routes/Models/PlayerScores');
var flash = require('connect-flash');

var node_restful = require ('node-restful');

app.use(logger('dev'));
require('./Config/passport')(passport);

app.use(cookieparser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));
app.set('view engine', 'ejs');
app.use(session({secret:'supernova',saveUninitialized:true,resave:true,store:new MongoStore({mongooseConnection:mongoose.connection})}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());





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
var positionx = {};
var positiony = {};
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

io.sockets.on('connection', function(socket){
    console.log('a user connected');

    socket.id = Math.random();
    //player.id = socket.id;

    Socket_List[socket.id] = socket;
    var player = Player(socket.id);
    player.id = socket.id;
    console.log(player.id);
    Player_List[socket.id] = player;
    //tell the player they connected, giving them their id
    socket.emit('onconnected', { id: socket.id } );
    socket .on('existing player',function(data){
       console.log(data) ;
    });
    socket.broadcast.emit('new player connected' ,{id: socket.id});
    //Useful to know when someone connects
    console.log('\t socket.io:: player ' + socket.id + ' connected');
    socket.on('hostCreateNewGame', function(data){
                console.log(data);
            //socket.room = 'room1';
        //socket.join('room1');
            thisGameId = ( Math.random() * 100000 ) | 0;

            socket.emit('newGameCreated', {gameId: thisGameId, mySocketId: socket.id,room:1});



           // console.log(""+'newGameCreated', {gameId: thisGameId, mySocketId: this.id});

            //Join the Room and wait for the players
            socket.join(thisGameId.toString());
                socket.room = thisGameId.toString();
            var clients = io.sockets.adapter.rooms[thisGameId.toString()];
            console.log(clients);


    }
    );
    var value = '';
var array  = {};
    var i = 0;
    socket.on('send_position',function(data){
        console.log(data);



          positionx[player.id] = data.x;
           positiony[player.id] = data.y;
        socket.broadcast.emit('update', {x:positionx[player.id] ,y:positiony[player.id],id:player.id});
        //io.sockets.emit('update',{x:positionx[player.id],y:positiony[player.id],id:player.id});




    });
    socket.on('update_score',function(data){
        console.log("score:"+data.scores);
        var playerScore = ''+data.scores;
        var playerData = new PlayerDatabase();
        playerData.scores.push("9");
        playerData.save(function(err){
            console.log('saved!');
        });

    });

io.sockets.emit('message',{message:'hello'});
    io.sockets.emit('message',{x:positionx[player.id],y:positiony[player.id],id:player.id});


    socket.on('sendMessage', function (data) {
        console.log(data.gameId);
        console.log(io.sockets.adapter.rooms[data.gameId]);
        if(io.sockets.adapter.rooms[data.gameId]!=undefined){
            socket.join(data.gameId);
            console.log(io.sockets.adapter.rooms[data.gameId]);
            socket.emit('joinPlayer',{message:'You successfully  joined the game'});
        }


        else {
            console.log("Sorry the following room doesn't exist");
            socket.emit('joinPlayer',{message:'Sorry the following room doesnot exist'});
        }

        //io.sockets.in(data.gameId).emit('updateGame',data);

        //io.sockets.in(socket.room).emit('updateGame', socket.id, data);
    });

    socket.on('hostCountdownFinished',function(){
        console.log('Game Started.');

    });

//TO DO LISTEN FOR JSON FROM THE CLIENT AND THEN TAKE THE JSON FROM THE CLIENT AND EMIT ALL THE POSITIONS WITH THEIR ID
    socket.on('disconnect', function(){
        console.log('user disconnected');
        console.log('\t socket.io:: client disconnected ' + socket.id );
        socket.leave(socket.room);
        socket.emit('playerleft',{message:'another player left the room'});
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
/*
socket.on('hostRoomFull',function(data){


        var sock = this;
        var data = {
            mySocketId : sock.id,
            gameId :data.gameId
        };

        try {

            io.sockets.in(data.gameId).emit('beginNewGame',data);
            // generates an exception
        }
        catch (e) {
            // statements to handle any exceptions
            console.log("Error!");// pass exception object to error handler
        }



    }
);
*/
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

function createEnemies(){
    var enemies = [];
    for(var i = 0; i < 10 ; i++){
        var xposition = Math.random();
        var yposition =Math.random();
        var jsonObject = {x:xposition,y:yposition};
        enemies.push(jsonObject);


    }

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
            number:player.number,
            id: player.id
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
