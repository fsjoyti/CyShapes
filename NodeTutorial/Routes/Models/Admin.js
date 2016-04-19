/**
 * Created by Anastasia on 3/20/2016.
 */
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var AdminSchema = new Schema({
    user: {
        id: { type: Schema.Types.ObjectId, ref: 'Players' },
        email: {type: String, default: ''}

},

    permissions: [{
        name: String,
        permit: Boolean
    }],
    timeCreated: { type: Date, default: Date.now }

});
AdminSchema.methods.hasPermissionTo = function(permission){
    for (var k = 0; k < this.permissions[k].name == permission;k++){
        if(this.permissions[k].permit){
            return true;
        }
        return false;
    }

};
module.exports = mongoose.model('Admin',AdminSchema);