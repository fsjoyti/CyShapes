/**
 * Created by Anastasia on 3/3/2016.
 */
var User = require('./Models/Users');
var nodemailer = require('nodemailer');
var async = require ('async');


module.exports = function(app,passport){
    app.get('/index',function(req,res){
        res.render('index.ejs');

    });

    app.get('/login',function(req,res) {
        res.render('login.ejs',{message:req.flash('LoginMessage')});
    });

    app.post('/login',passport.authenticate('local-login',{
            successRedirect :'/profile',
            failureRedirect : '/login',
            failureFlash : true
            }
        )
    );
    app.get('/signup',function(req,res){
        res.render('signup.ejs',{message:req.flash('signupMessage')});
    });

    app.post('/signup',passport.authenticate('local-signup',{
        successRedirect :'/login',
        failureRedirect : '/signup',
        failureFlash : true
        }
    ));

    app.get('/profile',isLoggedIn,function(req,res){
        res.render('profile.ejs',{user:req.user});
        }
    );

    app.get('/chat', function(req, res){
        res.render('chat.ejs');
    });
    app.get('/forgot', function(req, res) {
        res.render('Forgot.ejs');
    });
    app.post('/forgot',function(req,res,next){
        async.waterfall([
            function (done){

            }

        ])

    }
    );


    app.get('/logout',function(req,res){
        req.logout();
        res.redirect('/index');
        }
    );
};

function  isLoggedIn(req,res,next){
    if(req.isAuthenticated()){
        return next();
    }
    res.redirect ('/login');
}
