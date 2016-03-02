/**
 * Created by Anastasia on 2/22/2016.
 */
var io = require('socket.io-client');
var socket = io.connect('http://localhost:3000', {reconnect: true});
socket.on('connect', function(socket) {
    console.log('Connected!');
});
socket.emit('CH01', 'Sample Client', 'test msg');