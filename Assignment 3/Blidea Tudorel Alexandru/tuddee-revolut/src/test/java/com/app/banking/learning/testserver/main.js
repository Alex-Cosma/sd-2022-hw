const express = require("express");
const cors = require("cors");

const port = 5523;

const app = express();
app.use(
  cors({
    origin: "*",
  })
);

app.use(express.json());

app.get("/learnrequests/:toAdd", (req, res) => {
  const result = { ...req.query };
  const toAdd = req.params["toAdd"];
  for (const key of Object.keys(result)) {
    result[key] += toAdd;
  }
  res.status(200).json(result);
});

app.post("/learnrequests/:toAdd", (req, res) => {
  const result = { ...req.body };
  const toAdd = req.params["toAdd"];
  for (const key of Object.keys(result)) {
    result[key] += toAdd;
  }
  res.status(200).json(result);
});

app.put("/learnrequests/:fieldName/:newValue", (req, res) => {
  const result = { ...req.body };
  const { fieldName, newValue } = { ...req.params };
  result[fieldName] = newValue;
  res.status(200).send(result);
});

app.delete("/learnrequests/:fieldName", (req, res) => {
  const result = { ...req.body };
  const fieldName = req.params["fieldName"];
  delete result[req.params["fieldName"]];
  return res.status(200).json(result);
});

app.listen(port, () => {
  console.log(`Application listening to port ${port}`);
});
