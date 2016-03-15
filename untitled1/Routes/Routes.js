/**
 * Created by Anastasia on 3/3/2016.
 */
var User = require('./Models/Users');
var nodemailer = require('nodemailer');
var async = require ('async');
var crypto = require ('crypto');


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
        res.render('Forgot.ejs', {message:req.flash('info')});
    });
    app.post('/forgot',function(req,res,next){

        async.waterfall([
            function (done){
                crypto.randomBytes (20,function(err,buf){
                    var token = buf.toString();
                    done(err,token);
                });
            },
            function(token,done){
                User.findOne({'local.email' :  req.email },function(err,user){
                    if (!user) {
                       console.log("Error!");
                        req.flash('error', 'No account with that email address exists.');
                        return res.redirect('/forgot');
                    }
                    user.resetPasswordToken = token;
                    user.resetPasswordExpires = Date.now() + 7200000;
                    user.save(function(save){

                        done(err,token,user);

                    });
                });
            },
            function(token,user,done){

                var smtpTransport = nodemailer.createTransport("SMTP",{
                    service: "Gmail",
                    auth: {
                        user: "fam211092@gmail.com",
                        pass: "AnaSHINee21"
                    }
                });
                var mailOptions = {
                    to : user.email,
                    from:'passwordreset@cyshapes.com',
                    subject :'CyShapes Password reset',
                    text: 'You are receiving this because you (or someone else) have requested the reset of the password for your account.\n\n' +
                    'Please click on the following link, or paste this into your browser to complete the process:\n\n' +
                    'http://' + req.headers.host + '/reset/' + token + '\n\n' +
                    'If you did not request this, please ignore this email and your password will remain unchanged.\n'
                };
                smtpTransport.sendMail(mailOptions,function(err){
                    req.flash('info','An email has been sent to '+user.email + 'with further instructions');

                });
            }

        ]),
            function(err){
                if (err) return next(err);
                res.redirect('/forgot');
            }

    });


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
