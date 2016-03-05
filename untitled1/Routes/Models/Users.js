/**
 * Created by Anastasia on 3/3/2016.
 */
var mongoose = require('mongoose');
var bcrypt = require ('bcrypt-nodejs');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
    local:{
        email: String,
        password: String
    }

}

);

UserSchema.methods.generateHash = function (password){
    return bcrypt.hashSync(password,bcrypt.genSaltSync(8),null);

};
UserSchema.methods.validPassword = function (password){
    return bcrypt.compareSync(password,this.local.password);

};

module.exports = mongoose.model('Players',UserSchema);