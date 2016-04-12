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
    app.put('/admin/api/Players/:_id',function(req,res){
        var id = req.params._id;
        var Playerobject = req.body;
           User.findById(id,function(err,Player){
               if (err) throw err;

                if(!isEmpty(Playerobject.local.email))
                    Player.local.email = Playerobject.local.email;
               if(!isEmpty(Playerobject.local.password))
                   Player.local.password = Playerobject.local.password;
               if(!isEmpty(Playerobject.local.timeCreated))
                   Player.local.timeCreated = Playerobject.local.timeCreated;
               Player.save(function(err){
                   if(err) res.send(err);

                   res.json({message:'Player updated'});

               });


           });


    });
    app.delete('/admin/api/Players/:_id',function(req,res){
        var id = req.params._id;
        console.log(id);
        



        User.remove({_id:id},function(err,data){
            if(err) res.send(err);
            res.json({message:'Player deleted'});

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
function isEmpty(str) {
    return (!str || 0 === str.length);
}