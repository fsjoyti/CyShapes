/**
 * Created by Anastasia on 4/9/2016.
 */

var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var PlayerSchema = new Schema({

    //id: { type: Schema.Types.ObjectId, ref: 'Players' },
    //scores : {type:Array}
    scores:[
        {type: Schema.Types.ObjectId, ref: 'Players'}
    ]
    ///socket_id:  {type:String,default:''}
   
});


module.exports = mongoose.model('PlayerScores',PlayerSchema);
