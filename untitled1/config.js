/**
 * Created by Anastasia on 3/2/2016.
 */
var passport = require('passport');
module.exports.policies = {
    '*': [
        // Initialize Passport
        passport.initialize(),

        // Use Passport's built-in sessions
        passport.session()
    ]
}