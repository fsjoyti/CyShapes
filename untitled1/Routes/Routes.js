/**
 * Created by Anastasia on 3/3/2016.
 */
var User = require('./Models/Users');
var nodemailer = require('nodemailer');
var async = require ('async');
var crypto = require ('crypto');
var smtpTransport = require('nodemailer-smtp-transport');



module.exports = function(app,passport){
    app.get('/index',function(req,res){
        res.render('home.ejs');

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

    app.get('/profile',isnotBanned,function(req,res){

        res.render('profile.ejs',{user:req.user});
        }
    );

    app.get('/chat', function(req, res){
        res.render('chat.ejs');
    });
    app.get('/forgot', function(req, res) {
        res.render('Forgot.ejs', {message:req.flash('info')});
    });


    app.post('/forgot', function(req, res, next) {
        async.waterfall([
            function(done) {
                crypto.randomBytes(20, function(err, buf) {
                    var token = buf.toString('hex');
                    //console.log(""+token);
                    done(err, token);
                });
            },
            function(token, done) {
                User.findOne({'local.email' : req.body.email }, function(err, user) {
                    if (!user) {
                        console.log("Error!");
                        req.flash('error', 'No account with that email address exists.');
                        return res.redirect('/forgot');
                    }


                    user.local.resetPasswordToken = token;
                    user.local.resetPasswordExpires = Date.now() + 3600000; // 1 hour

                    user.save(function(err) {
                        //console.log(""+user.local.email);
                        //console.log(""+user.local.resetPasswordToken);
                        if(err) throw err;
                        done(null, token, user);
                    });
                });
            },
            function(token, user, done) {
                var options = {
                    service : 'gmail',
                    auth : {
                        user : 'fam211092@gmail.com',
                        password: 'AnaSHINee21@'
                    }


                };
                var transporter = nodemailer.createTransport('smtps://fam211092%40gmail.com:AnaSHINee21@smtp.gmail.com');

                /*
                var messages= [
                    'to: user.local.email',
                    'from: passwordreset@demo.com',
                    'subject: Node.js Password Reset',
                    'text: You are receiving this because you (or someone else) have requested the reset of the password for your account.\n\n' +
                    'Please click on the following link, or paste this into your browser to complete the process:\n\n' +
                    'http://' + req.headers.host + '/reset/' + token + '\n\n' +
                    'If you did not request this, please ignore this email and your password will remain unchanged.\n'
                ];
*/
                var messages = {
                    from : 'passwordreset@Cyshapes.com',
                    to   :  ''+user.local.email,
                    subject : 'Password reset for your CyShapes Account',
                    text : 'You are receiving this because you (or someone else) have requested the reset of the password for your account.\n\n' +
                'Please click on the following link, or paste this into your browser to complete the process:\n\n' +
                'http://' + req.headers.host + '/reset/' + token + '\n\n' +
                'If you did not request this, please ignore this email and your password will remain unchanged.\n'
                };
                transporter.sendMail(messages, function(err) {
                    req.flash('info', 'An e-mail has been sent to ' + user.local.email + ' with further instructions.');
                    done(err, 'done');
                });

                transporter.verify(function(error,success){
                    if(error){
                        console.log("Error!");
                    }
                    else{
                        console.log("Server is ready to take messages!");
                    }
                });
            }
        ], function(err) {
            if (err) return next(err);
            res.redirect('/forgot');
        });
    });
    app.get('/reset/:token', function(req, res) {
        User.findOne({'local.resetPasswordToken' : req.params.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function(err, user) {
            if (!user) {
                req.flash('error', 'Password reset token is invalid or has expired.');
                return res.redirect('/forgot');
            }
            console.log(''+req.params.token);
            res.render('PasswordReset.ejs',{message:req.flash('success')} );
        });
    });
    app.post('/reset/:token', function(req, res) {
        console.log(req.params.token);
        async.waterfall([
            function(done) {

                User.findOne({'local.resetPasswordToken' : req.params.token, 'local.resetPasswordExpires': { $gt: Date.now() } }, function(err, user) {
                    if (!user) {
                        req.flash('error', 'Password reset token is invalid or has expired.');
                        return res.redirect('/forgot');
                    }
                    user.local.password = user.generateHash(req.body.password);
                    user.local.resetPasswordToken = undefined;
                    user.local.resetPasswordExpires = undefined;
                    user.save(function(err) {
                        console.log(""+req.body.password);
                        console.log(""+user.local.password);

                        if(err) throw err;
                        done( null, user);
                    });

                });

            },

            function( user,done) {


                var transporter = nodemailer.createTransport('smtps://fam211092%40gmail.com:AnaSHINee21@smtp.gmail.com');


                var messages = {
                    from : 'passwordreset@Cyshapes.com',
                    to   :  ''+user.local.email,
                    subject : 'Your password for CyShapes Account',
                    text: 'Hello,\n\n' +
                    'This is a confirmation that the password for your account ' + user.local.email + ' has just been changed.\n'
                };
                transporter.sendMail(messages, function(err) {
                    req.flash('success', 'Success! Your password has been changed.');
                    done(err, 'done');
                });

                transporter.verify(function(error,success){
                    if(error){
                        console.log("Error!");
                    }
                    else{
                        console.log("Server is ready to take messages!");

                    }
                });
            }
        ], function(err) {
            if (err)  throw err;
             res.redirect('/index');
        });

    });
    app.get('/logout',function(req,res){
        req.logout();
        res.redirect('/index');
        }
    );
    
    
    app.get("/game", function(req, res) {
        // if we got here, the `app.all` call above has already
        // ensured that the user is logged in
        res.render('game.ejs');
    });
    
    app.get("/search", function(req, res) {
        // if we got here, the `app.all` call above has already
        // ensured that the user is logged in

        res.render('search.ejs');
    });
    

    app.post("/search", function(req, res) {

      
       
            
        User.findOne({ 'local.email' :  req.body.email }, function(err, user) {
            

            if (err)
                return done(err);


            if (!user) {
                //req.flash('searchMessage', 'the following user was not found!');
                //res.redirect('/search');
                res.json({success :false,message:'the following user was not found!'});

            } else {

                    var response = {success:true,user:user};
                res.json(response);
                

                //req.flash('searchMessage', 'the following user was  found!');
               //res.render('search.ejs',user);

            }
        });

    });
    app.all("/admin/*",isLoggedIn, isAdmin, function (req, res, next) {

        next();

    });

    app.get("/admin/users", function (req, res) {

        -        // if we got here, the `app.all` call above has already3
            // ensured that the user is logged in

            res.render('admin.ejs');

    });
app.get("/report",function (req,res) {
    User.find({'local.admin':true},function(error,data){
        if (error) throw error;
        res.json(data);
    });

});

    app.post("/report",function(req,res){

        /*
        User.find({'local.admin':true},function(error,data){
            if (error) throw error;
            res.json(data);
        });
        */
        var emailAdmin =  req.body.adminEmail;
        var usertoban = req.body.email;
        var report = req.body.report;


        var transporter = nodemailer.createTransport('smtps://fam211092%40gmail.com:AnaSHINee21@smtp.gmail.com');
        var messages = {
            from : 'frustratedUser@Cyshapes.com',
            to   :  ''+emailAdmin,
            subject : 'Request for ban',
            text : "Ban request for player:  "+ usertoban +" Reason: "+report +'\n\n'
            
        };

        transporter.sendMail(messages, function(err) {
           // req.flash('info', 'An e-mail has been sent to ' + user.local.email + ' with further instructions.');

            res.status('info').json('An e-mail has been sent to ' + emailAdmin + ' with your request.');
        });

        


    });

};





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

    if( req .user.local != undefined && req.user.local.admin ==false){
        res.redirect ('/profile');

    }


}

function isnotBanned(req,res,next) {
    if( req .user.local != undefined && req.user.local.banned ==false){

        return next();
    }
    if( req .user.local != undefined && req.user.local.banned ==true){
        res.redirect ('/index');

    }



}