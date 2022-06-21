const express = require("express");
const cors = require("cors");
const fs = require("fs");
const bodyParser = require("body-parser");
const { imageExists, getImagePath, addImage } = require("./imageSearcher");

const config = JSON.parse(fs.readFileSync("config.json"));

const app = express();
app.use(cors(config.cors));
app.use(bodyParser.json({
    limit: '150mb',
  })
);

app.get("/images/:imageName", (req, res) => {
      const imageName = req.params["imageName"];
      try {
            if (imageExists(imageName)) {
                  res.status(200).sendFile(getImagePath(imageName));
            } else {
                  res.sendStatus(404);
            }
      } catch (err) {
            res.sendStatus(404);
      }
});

app.post("/images", (req, res) => {
      const imageName = req.body["imageName"];
      const image = req.body["image"];
      const imageNewName = addImage(imageName, image);
      res.status(200).sendFile(getImagePath(imageNewName));
});


app.listen(config.port, () => {
      console.log(`Application started on port ${config.port}`);
});
