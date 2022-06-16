const CloudDb = require("../utils/clouddb");
const UserModel = require("../model/user");
const { v4: uuidv4 } = require("uuid");

async function getUserByUserId(userId) {
  console.log("getUserByUserId:" + userId);
  const database = new CloudDb.Database();
  const query = database.createQuery(UserModel.User, [
    { filter: "eq", field_name: "userID", field_value: userId },
  ]);
  const item = await database.readItems(query);
  return item;
}

async function putUser(params){
    console.log("putUser: "+JSON.stringify(params));


}

module.exports = {
    getUserByUserId
};
