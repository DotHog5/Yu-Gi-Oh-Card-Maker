const fs = require('fs');
const router = require('express').Router();
const card = require('../controller/Card');

router.post('/input', (req, res) => {
  card.inputDataCard(req.body)
    .then(result => res.json(result))
    .catch(err => res.json(err))
});

router.get('/cards', (req, res) => {
  card.showCard()
    .then(result => res.json(result))
    .catch(err => res.json(err))
})

router.get('/cards/:id', (req, res) => {
  card.showSingleCard(req.params.id)
    .then(result => res.json(result))
    .catch(err => res.json(err))
})

router.delete('/delete/:id', (req, res) => {
  card.deleteCard(req.params.id)
    .then(result => res.json(result))
    .catch(err => res.json(err))
})

router.put('/change/:id', (req, res) => {
  card.updateCard(req.params.id, req.body)
    .then(result => res.json(result))
    .catch(err => res.json(err))
})

module.exports = router;