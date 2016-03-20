/**
 * Created by Anastasia on 3/20/2016.
 */
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var AdminGroupSchema = new Schema({
    _id: { type: String },
    permissions: [{ name: String, permit: Boolean }]
});
module.exports = mongoose.model('AdminGroup',AdminSchema);