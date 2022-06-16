var { AGCClient, CredentialParser } = require("@agconnect/common-server");
const clouddb = require("@agconnect/database-server/dist/index.js");
const agconnect = require("@agconnect/common-server");

let mCloudDBZone = null;

class Database {
  async readItems(query) {
    try {
      const resp = await mCloudDBZone.executeQuery(query);
      let result = JSON.parse(JSON.stringify(resp));
      return result.snapshotObjects;
    } catch (error) {
      console.warn("readItems=>", error);
    }
    return null;
  }

  createQuery(objType, expressions) {
    let query = clouddb.CloudDBZoneQuery.where(objType);
    if (expressions) {
      expressions.forEach((exp) => {
        if (exp.filter == "eq") {
          query = query.equalTo(exp.field_name, exp.field_value);
        }
      });
    }

    return query;
  }

  async putItem(item) {
    try {
      const resp = await mCloudDBZone.executeUpsert(item);
      return resp;
    } catch (error) {
      console.warn("upsertBookInfo=>", error);
    }
    return -1;
  }
}

async function initCloudDB() {
  const dbZone = process.env.CLOUD_ZONE;
  const credentialPath = process.env.CLOUD_CREDENTIALS_PATH;

  agconnect.AGCClient.initialize(
    agconnect.CredentialParser.toCredential(credentialPath),
    "clientDE",
    "DE"
  );

  const agcClient = agconnect.AGCClient.getInstance("clientDE");
  clouddb.AGConnectCloudDB.initialize(agcClient);
  const agconnectCloudDB = clouddb.AGConnectCloudDB.getInstance(agcClient);

  const cloudDBZoneConfig = new clouddb.CloudDBZoneConfig(dbZone);
  const zone = agconnectCloudDB.openCloudDBZone(cloudDBZoneConfig);
  mCloudDBZone = zone; 
}

module.exports = { Database, initCloudDB, mCloudDBZone };
