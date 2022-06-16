const Pet = require("../model/pet.js");
const User = require("../model/user.js");
const { v4: uuidv4 } = require("uuid");
const databaseCtrl = require("../utils/clouddb");


async function getPet() {
  const pets = await database.readItems(Pet.Pet);
  return pets;
}

async function putPet(index, userId) {
  const pet = new Pet.Pet();
  pet.setPetID(uuidv4());
  pet.setPetName("Pet"+index);
  pet.setOwnership(userId);
  pet.setPetDesc("Desc"+index);
  
  return await database.putItem(pet);
}

async function putUser(index) {
  const user = new User.User();
  user.setCreated_at(Date.now());
  user.setRemark("remark"+index);
  user.setUserName("UserName"+index);
  user.setUserType("user"+index);
  user.setPhotoUrl("url"+index);
  user.setLast_login(0);
  user.setUserID(uuidv4());
  await database.putItem(user);
  return user; 
}

async function feed() {
  console.log("-->feed");
  try {
    await init();
    let user = await putUser(1); 
    await putPet(1, user.getUserID());
    user = await putUser(2); 
    await putPet(2, user.getUserID());  
    user = await putUser(3); 
    await putPet(3, user.getUserID());
    user = await putUser(4); 
    await putPet(4, user.getUserID());
  
  } catch (error) {
    console.log(error);
  }
}

module.exports = {
  feed,
};
