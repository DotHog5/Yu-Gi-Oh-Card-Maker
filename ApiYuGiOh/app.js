const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const mongoose = require('mongoose');
const port = process.env.PORT || 5050;
const dbConfig = require('./config/dbConfig');
const cors = require('cors');
const path = require('path');

mongoose.connect(dbConfig.mongoURL, {
  useNewUrlParser: true
}).then(() => console.log('connected to mongodb'))
    .catch(err => console.log(err))
 
app.use(cors());

app.use(bodyParser.json({
  extended: true,
  limit: '50mb'
}));

app.use(bodyParser.urlencoded({
  extended: true,
  limit: '50mb'
}));


app.use('/user', require('./routes/User'));
app.use('/card', require('./routes/Card'));

app.listen(port, function() {
  console.log('Server is running on port ' + port);
})