const dbName = process.env.SPLIT_EXPENSE_DB;
const splitExpenseUser = process.env.SPLIT_EXPENSE_DB_USER
const splitExpensePassword = process.env.SPLIT_EXPENSE_DB_PASSWORD

if (!(dbName && splitExpenseUser && splitExpensePassword)) {
    throw new Error('Environment variables SPLIT_EXPENSE_DB, SPLIT_EXPENSE_USER, and SPLIT_EXPENSE_PASSWORD must be configured')
}


const result = db.adminCommand({ listDatabases: 1 });

const exists = result.databases.some(({ name }) => name === dbName);

print(`${dbName} exists: ${exists}`);

if (!exists) {
    print(`Creating ${dbName} with collections "users" and "groups...`)
    const newDb = db.getSiblingDB("split_expenses");

    newDb.createCollection("users")
    newDb.createCollection("groups")
}

const adminDb = db.getSiblingDB("admin")
const userInfo = adminDb.runCommand({usersInfo: 1})


const splitExpenseUserExists = userInfo.users.some(
    ({ user, db }) => user === splitExpenseUser && db === "admin"
);

print(`${splitExpenseUser} exists: ${splitExpenseUserExists}`);

if (!splitExpenseUserExists) {
    print(`Creating user: ${splitExpenseUser}`)
    adminDb.createUser({
            user: splitExpenseUser,
            pwd: splitExpensePassword,
            roles: [{role: "readWrite", db: dbName}]
        }
    )
}



