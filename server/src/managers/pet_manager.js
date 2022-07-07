const CloudDb = require("../utils/clouddb");
const PetInfo = require("../model/pet");
const user_mng = require("./user_manager");
const PetTraking = require("../model/petTrack");
const { v4: uuidv4 } = require("uuid");

async function getPetsByUserId(userId) {
  console.log("getPetsByUserId:" + userId);
  let database = new CloudDb.Database();
  const query = database.createQuery(PetInfo.Pet, [
    { filter: "eq", field_name: "Ownership", field_value: userId },
  ]);
  const items = database.readItems(query);
  return items;
}

async function getPetByPetId(petId) {
  console.log("getPetByPetId: " + petId);
  let database = new CloudDb.Database();
  const query = database.createQuery(PetInfo.Pet, [
    { filter: "eq", field_name: "PetID", field_value: petId },
  ]);
  const items = database.readItems(query);
  return items;
}

async function putPet(params) {
  console.log("putPet:" + JSON.stringify(params));
  const users = await user_mng.getUserByUserId(params.user_id);
  if (users.length > 0) {
    const pet = new PetInfo.Pet();
    pet.setPetID(uuidv4());
    pet.setPetName(params.pet_name);
    pet.setOwnership(params.user_id);
    pet.setPetDesc(params.pet_type);

    let database = new CloudDb.Database();
    await database.putItem(pet);
    return {
      pet_id: pet.getPetID(),
      user_id: pet.getOwnership(),
    };
  } else {
    throw "User does not exist";
  }
}

async function putPetLocation(params) {
  console.log("putPetLocation: " + JSON.stringify(params));
  const pets = await getPetByPetId(params.pet_id);
  if (pets.length > 0) {
    const petTrack = new PetTraking.PetTrack();
    petTrack.setRecordID(uuidv4());
    petTrack.setPetID(params.pet_id);
    petTrack.setDate(Date.now().toString());
    petTrack.setLatitude(params.lat);
    petTrack.setLongtitude(params.lon);
    petTrack.setRemark("");

    let database = new CloudDb.Database();
    await database.putItem(petTrack);
    return {
      pet_id: petTrack.getPetID(),
      record_id: petTrack.getRecordID(),
    };
  } else {
    throw "PetID does not exist";
  }
}

async function getPetLocationByPetId(params) {
  console.log("getPetLocationByPetId: " + JSON.stringify(params));
  const pets = await getPetByPetId(params.pet_id);
  if (pets.length > 0) {
    let database = new CloudDb.Database();
    const query = database.createQuery(PetTraking.PetTrack, [
      { filter: "eq", field_name: "PetID", field_value: params.pet_id },
    ]);
    query.orderByAsc("Date");
    const items = database.readItems(query);
    return items;
  } else {
    throw "PetID does not exist";
  }
}

async function putBulkPetLocation(params) {
  console.log("putBulkPetLocation: " + JSON.stringify(params));
  const pets = await getPetByPetId(params.PetID);
  let result = [];
  for (item of params.data) {
    const values = {
      PetID: params.PetID,
      Lat: item[0].toString(),
      Lng: item[1].toString(),
    };
    const temp = await putPetLocation(values);
    result.push(temp);
  }
  return result;
}

async function deletePetByPetId(params) {
  console.log("deletePetByPetId: " + JSON.stringify(params));
  let database = new CloudDb.Database();
  const pet = new PetInfo.Pet();
  pet.setPetID(params.pet_id);
  await database.deleteItem(pet)
}

async function deleteAllPetLocationsByPetId(params){
  console.log("deleteAllPetLocationsByPetId: " + JSON.stringify(params));
  let database = new CloudDb.Database();
  const locations = await getPetLocationByPetId(params);
  for (let loc of locations){
    console.log("joc: "+JSON.stringify(loc));
    const tracking = new PetTraking.PetTrack(); 
    tracking.setRecordID(loc.RecordID);
    await database.deleteItem(tracking);
  }  
}


module.exports = {
  getPetsByUserId,
  putPet,
  putPetLocation,
  getPetLocationByPetId,
  putBulkPetLocation,
  deletePetByPetId,
  deleteAllPetLocationsByPetId
};
