/**
 * Created by Anastasia on 4/9/2016.
 */

var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var PlayerSchema = new Schema({


    scores : {type:String,default:''},
    id:  {type:String,default:''}
});


module.exports = mongoose.model('PlayerScores',PlayerSchema);