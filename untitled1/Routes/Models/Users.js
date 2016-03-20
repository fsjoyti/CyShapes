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
        roles: {
            admin: { type: mongoose.Schema.Types.ObjectId, ref: 'Admin' },
            account: { type: mongoose.Schema.Types.ObjectId, ref: 'Account' }
        },
        isActive: String,
        timeCreated: { type: Date, default: Date.now },
        resetPasswordToken: String,
        resetPasswordExpires: Date


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
userSchema.methods.defaultReturnUrl = function() {
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
