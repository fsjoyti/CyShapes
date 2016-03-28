/**
 * Created by Anastasia on 3/20/2016.
 */
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var AccountSchema = new Schema({
    user: {
        id: { type: Schema.Types.ObjectId, ref: 'Players' },
        email: {type: String, default: ''}

    },

        timeCreated: { type: Date, default: Date.now }

});
module.exports = mongoose.model('Account',AccountSchema);