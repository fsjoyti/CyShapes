/**
 * Created by Anastasia on 3/18/2016.
 */
var User = require('../Routes/Models/Users');
var PlayerScores = require('../Routes/Models/PlayerScores');
var ObjectId = require('mongodb').ObjectId;
module.exports = function(app){

    app.all("/admin/*",  isLoggedIn,isAdmin,function(req, res, next) {
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

        var newUser            = new User();
        newUser.local.email = Player.local.email;
        newUser.local.password = newUser.generateHash(Player.local.password);
        newUser.local.timeCreated =Player.local.timeCreated;
        newUser.local.admin = false;
        newUser.local.banned = false;

        newUser.save(function(error,data){
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
         
                if(Playerobject.local.email)
                    Player.local.email = Playerobject.local.email;
               if(Playerobject.local.password)
                   Player.local.password = Player.generateHash(Playerobject.local.password);
               if(Playerobject.local.timeCreated)
                   Player.local.timeCreated = Playerobject.local.timeCreated;

               if(Playerobject.local.admin !=undefined){
                   Player.local.admin = Playerobject.local.admin;
               }
               if(Playerobject.local.banned !=undefined){
                   Player.local.banned = Playerobject.local.banned;
               }
               if(Player!=null) {
                   Player.save(function (err) {

                       if (err) res.send(err);

                       res.json({message: 'Player updated'});

                   });
               }

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

function isAdmin(req,res,next){
    
    if( req .user.local != undefined && req.user.local.admin ==true){

        return next();
    }

    res.redirect ('/profile');


}