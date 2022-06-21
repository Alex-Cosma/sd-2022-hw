const express = require('express');
const cors = require('cors');
const fs = require('fs');
const { makeASimpleFacebookPost, makeAComplexFacebookPost } = require('./facebookNotification');
const { configureTransporter, sendSimpleMail, sendWelcomeMail, sendCurrencyUpdate } = require('./mailNotification');

const config = JSON.parse(fs.readFileSync("config.json"));

configureTransporter(config.mail)

const app = express();

app.use(cors(config.cors));
app.use(express.json());

const mainHandler = (error, info) => {
      if (error) {
          console.log(error);
        } else {
          console.log("Email sent: " + info.response);
        }
}

app.post("/token/:serviceName", (req, res) => {
      if (!req.body || !req.body["token"]) {
            res.sendStatus(400);
      } else {
            console.log("Configuring the token with: " + req.body["token"]);
            config.tokens[req.params["serviceName"].toLowerCase()] = req.body["token"];
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/facebook/simple", (req, res) => {
      if (!req.body || !req.body["message"]) {
            res.sendStatus(400);
      } else {
            makeASimpleFacebookPost(config.tokens.facebook, req.body["message"]);
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/facebook/complex", (req, res) => {
      if (!req.body || !req.body["elements"]) {
            res.sendStatus(400);
      } else {
            console.log(req.body);
            makeAComplexFacebookPost(config.tokens.facebook, req.body);
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/mail/config", (req, res) => {
      if (!req.body) {
            res.sendStatus(400);
      } else {
            console.log("Configuring the mail with... " + JSON.stringify(req.body));
            config.mail = req.body;
            configureTransporter(req.body);
            config.mail["from"] = req.body["auth"]["user"];
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/mail/simple", (req, res) => {
      if (!req.body) {
            res.sendStatus(400);
      } else {
            sendSimpleMail(mainHandler, {...req.body, "from": config.mail["from"]});
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/mail/welcome", (req, res) => {
      if (!req.body) {
            res.sendStatus(400);
      } else {
            const {from, to, offers} = {...req.body, "from": config.mail["from"]};
            sendWelcomeMail(mainHandler, from, to, offers);
            res.sendStatus(config.baseOkStatus);
      }
});

app.post("/mail/sendCurrencyUpdate", (req, res) => {
      if (!req.body) {
            res.sendStatus(400);
      } else {
            const {from, recipients, currencies} = {...req.body, "from": config.mail["from"]};
            sendCurrencyUpdate(mainHandler, from, recipients, currencies);
            res.sendStatus(config.baseOkStatus);
      }
})

app.get("/config", (req, res) => {
      res.status(200).json(config);
})


app.listen(config.port, () => {
      console.log(`Application started on port: ${config.port}`);
})