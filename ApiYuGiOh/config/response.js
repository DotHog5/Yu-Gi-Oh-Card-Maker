module.exports = {
  commonError: {
    error: true,
    msg: "There is an error on the server"
  },
  commonErrorMessage: (msg) => {
    return {
      err: true,
      msg: msg
    }
  },
  commonSuccess: {
    error: false,
    msg: "Succeded the request"
  },
  commonSuccessMessage: (msg) => {
    return {
      err: false,
      msg: msg
    }
  },
  commonResult: (data) => {
    return {
      error: false,
      msg: 'Succesfully loading',
      data: data
    }
  }
}