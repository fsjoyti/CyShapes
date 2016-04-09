/**
 * Created by Anastasia on 4/9/2016.
 */

var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var PlayerSchema = new Schema({


    scores : {type:String,default:''},
    socket_id:  {type:String,default:''},
    id: { type: Schema.Types.ObjectId, ref: 'Players' }
});


module.exports = mongoose.model('PlayerScores',PlayerSchema);
