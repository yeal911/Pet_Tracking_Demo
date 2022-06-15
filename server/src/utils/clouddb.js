var { AGCClient, CredentialParser } = require("@agconnect/common-server");
const clouddb = require("@agconnect/database-server/dist/index.js");
const agconnect = require("@agconnect/common-server");

class Database {
  dbZone;
  credentialPath;
  mCloudDBZone;
  database;

  constructor(dbZone, credentialPath) {
    this.dbZone = dbZone;
    this.credentialPath = credentialPath;
  }
  
  async init() {
    this.database = Database(this.dbZone, credentialPath);
    agconnect.AGCClient.initialize(
      agconnect.CredentialParser.toCredential(this.credentialPath),
      "clientDE",
      "DE"
    );

    const agcClient = agconnect.AGCClient.getInstance("clientDE");
    clouddb.AGConnectCloudDB.initialize(agcClient);
    const agconnectCloudDB = clouddb.AGConnectCloudDB.getInstance(agcClient);

    const cloudDBZoneConfig = new clouddb.CloudDBZoneConfig(this.dbZone);
    this.mCloudDBZone = agconnectCloudDB.openCloudDBZone(cloudDBZoneConfig);
  }

  async readItems(query) {
    try {
      const resp = await this.mCloudDBZone.executeQuery(query);
      let result = JSON.parse(JSON.stringify(resp));
      return result.snapshotObjects;
    } catch (error) {
      console.warn("readItems=>", error);
    }
    return null;
  }

  createQuery(objType, expressions) {
    let query = clouddb.CloudDBZoneQuery.where(objType);
    expressions.forEach((exp) => {
      if (exp.filter == "eq") {
        query = query.equalTo(exp.field_name, exp.field_value);
      }
    });
    return query;
  }

  async putItem(item) {
    try {
      const resp = await this.mCloudDBZone.executeUpsert(item);
      return resp;
    } catch (error) {
      console.warn("upsertBookInfo=>", error);
    }
    return -1;
  }
}

module.exports = { Database };
