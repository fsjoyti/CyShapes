/**
 * Created by Anastasia on 3/18/2016.
 */
var User = require('../Routes/Models/Users');
module.exports = function(app){
    app.get('/admin/users',function(req,res){
        User.find({},function(error,data){
           res.json(data);
        });
    });
}