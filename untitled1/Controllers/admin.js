/**
 * Created by Anastasia on 3/18/2016.
 */
var User = require('../Routes/Models/Users');
var PlayerScores = require('../Routes/Models/PlayerScores');
var ObjectId = require('mongodb').ObjectId;
module.exports = function(app){

    app.all("/admin/*",isLoggedIn , function(req, res, next) {
        next();
    });
    app.get('/admin/api/Players',function(req,res){

        User.find({},function(error,data){
            if (error) throw error;
            res.json(data);
        });
    });

    app.post('/admin/api/Players',function(req,res){
            var Player = req.body;
        console.log(Player);
        User.create(Player,function(error,data){
            if (error) throw error;
            res.json(data);
        });

    });
    app.get('/admin/api/Players/:_id',function(req,res){

        User.findById(req.params._id,function(error,data){
            if (error) throw error;
            res.json(data);
        });
    });
    app.get('/admin/api/PlayerScores',function(req,res){

        PlayerScores.find({},function(error,data){
            if (error) throw error;
            res.json(data);
        });
    });
}

function  isLoggedIn(req,res,next){
    if(req.isAuthenticated()){
        return next();
    }
    res.redirect ('/login');
}