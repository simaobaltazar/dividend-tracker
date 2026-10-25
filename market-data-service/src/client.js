const grpc = require('@grpc/grpc-js');
const loader = require('@grpc/proto-loader');
const path = require('node:path');

const def = loader.loadSync(path.join(__dirname, '../../proto/market_data.proto'), {
  keepCase: true, longs: String, defaults: true, oneofs: true
});
const api = grpc.loadPackageDefinition(def).marketdata;

const client = new api.MarketDataService('localhost:50051', grpc.credentials.createInsecure());

const ticker = process.argv[2] || 'AAPL';
client.GetQuote({ ticker }, (err, response) => {
  if (err) return console.error('Erro:', err.message);
  console.log(response);
});