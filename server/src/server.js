const express = require("express");
const http = require("http");
const url = require("url");
const bodyParser = require("body-parser");
const feeder = require("./managers/feeder");
const pet_mng = require("./managers/pet_manager");

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const host = "localhost";
const port = 8080;

app.post("/feeder", (req, res) => {
  const body = req.body;
  console.log(body);
  feeder.feed().then(() => {
    res.sendStatus(200);
  });
});

async function petCtr(body) {
  switch (body.action) {
    case "GETPETSBYUSERID": {
      return await pet_mng.getPetsByUserId(body.values.user_id);
    }
  }
}

app.post("/pets", (req, res) => {
  console.log("-->pets");
  const body = req.body;

  petCtr(body).then((value) => {
    console.log(JSON.stringify(value));
    res.status(200).send(value);
  }).catch(error =>{
    res.sendStatus(500);
  });
});

app.listen(port, () => {
  console.log(`PetTracking ${port}`);
});
