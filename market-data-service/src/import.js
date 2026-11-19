const grpc = require('@grpc/grpc-js');
const loader = require('@grpc/proto-loader');
const path = require('node:path');
const fs = require('node:fs');

const def = loader.loadSync(path.join(__dirname, '../../proto/transaction_import.proto'), {
  keepCase: true, longs: String, defaults: true, oneofs: true
});
const api = grpc.loadPackageDefinition(def).transactionimport;

const client = new api.TransactionImportService('localhost:9090', grpc.credentials.createInsecure());

const file = process.argv[2] || path.join(__dirname, '../data/transactions.csv');
const lines = fs.readFileSync(file, 'utf8').trim().split('\n').slice(1);

const call = client.ImportTransactions((err, summary) => {
  if (err) return console.error('Erro:', err.message);
  console.log(`\nImportadas: ${summary.imported}`);
  console.log(`Rejeitadas: ${summary.rejected}`);
  summary.errors.forEach(e => console.log(`  - ${e}`));
});

for (const line of lines) {
  const [type, quantity, price, date, asset_ticker] = line.trim().split(',');
  console.log(`A enviar: ${type} ${quantity} ${asset_ticker}`);
  call.write({ type, quantity, price, date, asset_ticker });
}

call.end();