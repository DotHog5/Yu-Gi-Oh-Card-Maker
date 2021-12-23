const userModel = require('../model/User.js');
const response = require('../config/response');
const bcrypt = require('bcrypt');

exports.registration = (data) => 
  new Promise(((resolve, reject) => {
    userModel.findOne({userName: data.userName, email: data.email})
    .then(user => {
      if (user) {
          resolve(response.commonErrorMessage("User already exist"));
        } else {
          bcrypt.hash(data.password, 10, (err, hash) => {
            if (err) {
              reject(response.commonErrorMessage);
            } else {
              data.password = hash;
              userModel.create(data)
                .then(() => resolve(response.commonSuccessMessage('Registration Succesfull!')))
                .catch(() => reject(response.commonErrorMessage('Registration failed. Please try again.')))
            }
          })
        }
      }).catch(() => reject.commonError)
  }));

exports.login = (data) => 
  new Promise(((resolve, reject) => {
    userModel.findOne({
      userName: data.userName
    }).then(user => {
      if (user) {
        if (bcrypt.compareSync(data.password, user.password)) {
          resolve(response.commonResult(user));
        } else {
          reject(response.commonErrorMessage('Password is not correct'))
        }
      } else {
        reject(response.commonErrorMessage('Username not found'));
      }
    })
  }))
