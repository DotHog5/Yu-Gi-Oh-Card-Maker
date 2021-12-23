const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
  cardCode: {
    type: String
  },
  cardName: {
    type: String
  },
  cardKind: {
    type: String
  },
  cardType: {
    type: String
  },
  cardAttr: {
    type: String
  },
  cardAtt: {
    type: Number
  },
  cardDef: {
    type: Number
  },
  cardEff: {
    type: String
  }
});

module.exports = mongoose.model('card', userSchema);