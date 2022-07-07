const express = require("express");
const http = require("http");
const url = require("url");
const bodyParser = require("body-parser");
const feeder = require("./managers/feeder");
const pet_mng = require("./managers/pet_manager");
const user_mng = require("./managers/user_manager");
const config = require("dotenv").config();
const database = require("./utils/clouddb");
const { AGCAuth } = require("@agconnect/auth-server");

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const host = "localhost";
const port = 8080;

console.log(process.env.CLOUD_ZONE);
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
    case "PUTPET": {
      return await pet_mng.putPet(body.values);
    }
    case "PUTPETLOCATION": {
      return await pet_mng.putPetLocation(body.values);
    }
    case "GETPETLOCATIONBYPETID": {
      return await pet_mng.getPetLocationByPetId(body.values);
    }
    case "PUTBULKPETLOCATION": {
      return await pet_mng.putBulkPetLocation(body.values);
    }
    case "DELETEPETBYPETID": {
      return await pet_mng.deletePetByPetId(body.values);
    }
    case "CLEARALLPETLOCATIONSBYPETID": {
      return await pet_mng.deleteAllPetLocationsByPetId(body.values);
    }
  }
}

async function userCtr(body) {
  switch (body.action) {
    case "GETUSERBYUSERID": {
      return await user_mng.getUserByUserId(body.values.user_id);
    }
    case "PUTUSERINFO": {
      return await user_mng.putUser(body.values);
    }
    default: {
      throw new Error("Operation not supported");
    }
  }
}

app.post("/pets", (req, res) => {
  console.log("-->pets");
  const body = req.body;

  petCtr(body)
    .then((value) => {
      console.log(JSON.stringify(value));
      res.status(200).send(value);
    })
    .catch((error) => {
      console.log(error);
      res.status(500).send(error);
    });
});

app.post("/users", (req, res) => {
  const body = req.body;
  userCtr(body)
    .then((value) => {
      console.log(JSON.stringify(value));
      res.status(200).send(value);
    })
    .catch((error) => {
      res.status(500).send(error);
    });
});

function getAccessToken() {
  agc;
}

app.listen(port, () => {
  database.initCloudDB().then(() => {
    console.log(`PetTracking ${port}`);
  });
});
