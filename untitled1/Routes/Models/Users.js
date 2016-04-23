/**
 * Created by Anastasia on 3/3/2016.
 */
var mongoose = require('mongoose');
var bcrypt = require ('bcrypt-nodejs');
var Schema = mongoose.Schema;

var UserSchema = new Schema({
    local:{
        email: { type: String, unique: true } ,
        password: String,
        admin : Boolean,
        banned: Boolean,
        isActive: String,
        timeCreated: { type: Date, default: Date.now },
        resetPasswordToken: String,
        resetPasswordExpires: Date,
        
        scores: [{
            type: Schema.Types.ObjectId,
            ref: 'PlayerScores' 
        }]


    }

}

);

UserSchema.methods.canPlayRoleOf = function(role) {
    if (role === "admin" && this.roles.admin) {
        return true;
    }

    if (role === "account" && this.roles.account) {
        return true;
    }

    return false;
};
UserSchema.methods.defaultReturnUrl = function() {
    var returnUrl = '/index';


    if (this.canPlayRoleOf('admin')) {
        returnUrl = '/admin/';
    }

    return returnUrl;
};
UserSchema.methods.generateHash = function (password){
    return bcrypt.hashSync(password,bcrypt.genSaltSync(8),null);

};
UserSchema.methods.validPassword = function (password){
    return bcrypt.compareSync(password,this.local.password);

};

module.exports = mongoose.model('Players',UserSchema);
