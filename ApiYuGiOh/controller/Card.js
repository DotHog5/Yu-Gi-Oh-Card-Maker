const card = require('../model/Card');
const response = require('../config/response');
const mongoose = require('mongoose');
const ObjectId = mongoose.Types.ObjectId;

exports.inputDataCard = (data) =>
  new Promise(async (resolve, reject) => {
    const newCard = new card({
      cardCode: data.cardCode,
      cardName: data.cardName,
      cardKind: data.cardKind,
      cardType: data.cardType,
      cardAttr: data.cardAttr,
      cardAtt: data.cardAtt,
      cardDef: data.cardDef,
      cardEff: data.cardEff,
    });

    await card.findOne({cardCode: data.cardCode})
    .then(card => {
        if (card) {
          reject(response.commonErrorMessage('Card already exists'))
        } else {
          newCard.save()
            .then(() => resolve(response.commonSuccessMessage('Card was succesfully put in!')))
            .catch(() => reject(response.commonErrorMessage('Card was not put in. Please try again.'))
          )
        }
      }).catch(() => {reject.commonError})
  });

exports.showCard = () => 
  new Promise(async (resolve, reject) => {
    await card.find({})
      .then(result => {
        resolve(response.commonResult(result));
      })
      .catch(() => reject.commonError)
  })

exports.showSingleCard = (newCard) => 
  new Promise(async (resolve, reject) => {
    await card.findOne({cardCode: newCard})
      .then(result => {
        resolve(response.commonResult(result));
      })
      .catch(() => reject.commonError)
  })


exports.updateCard = (id, data) =>
new Promise(async (resolve, reject) => {
  await card.updateOne(
      {_id: ObjectId(id)},
      {
        $set: {
          cardCode: data.cardCode,
          cardName: data.cardName,
          cardKind: data.cardKind,
          cardType: data.cardType,
          cardAttr: data.cardAttr,
          cardAtt: data.cardAtt,
          cardDef: data.cardDef,
          cardEff: data.cardEff,
        }
      }
    )
    .then(card => {
      resolve(response.commonSuccessMessage('Changes were made'))
    })
    .catch(err => {
      reject(response.commonErrorMessage('No changes were made'))
    })
  })

exports.deleteCard = (id) =>
  new Promise(async (resolve, reject) => {
    await card.deleteOne({_id: ObjectId(id)})
      .then(() => {
        resolve(response.commonSuccessMessage('Card deleted'))
      })
      .catch(() => {
        reject(response.commonErrorMessage('Card was not deleted'))
      })
  })