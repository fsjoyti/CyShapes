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
    groups: [{ type: String, ref: 'AdminGroup' }],
    permissions: [{
        name: String,
        permit: Boolean
    }],
    timeCreated: { type: Date, default: Date.now }

});
module.exports = mongoose.model('Admin',AdminSchema);