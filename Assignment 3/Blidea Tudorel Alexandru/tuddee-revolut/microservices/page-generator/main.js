const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
const fs = require("fs");

const config = JSON.parse(fs.readFileSync("config.json"));

const app = express();
app.use(cors(config.corsConfig));

app.get("/", (req, res) => {
  console.log("Getting hello!");
  res.send("Hello");
});

mongoose
  .connect(`${config.mongoBaseUrl}/${config.database}`)
  .then(() => {
    console.log(`Connected to ${config.database}`);
    app.listen(config.port, () => {
      console.log(`Server started at port ${config.port}`);
    });
  })
  .catch((err) => console.error(err));
