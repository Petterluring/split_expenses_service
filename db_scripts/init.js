const dbName = "split_expenses";

const result = db.adminCommand({ listDatabases: 1 });

const exists = result.databases.some(database => database.name === dbName);

print(`${dbName} exists: ${exists}`);