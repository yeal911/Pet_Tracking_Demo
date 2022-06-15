const clouddb = require("../utils/clouddb");

async function getPetsByUserId(userId){
    console.log("getPetsByUserId:"+userId);
    clouddb.createQuery();
    
    return {
        info: "Hola"
    };
}



module.exports = {
    getPetsByUserId
}