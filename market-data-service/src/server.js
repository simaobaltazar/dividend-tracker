const grpc = require('@grpc/grpc-js');
const loader = require('@grpc/proto-loader');
const path = require('node:path');

const def = loader.loadSync(path.join(__dirname, '../../proto/market_data.proto'), {
  keepCase: true, longs: String, defaults: true, oneofs: true
});
const api = grpc.loadPackageDefinition(def).marketdata;

function GetQuote(call, cb) {
  const quotes = {
    'AAPL':   { ticker: 'AAPL',   price: '190.10', currency: 'USD', annual_dividend: '1.04', payments_per_year: 4 },
    'EDP.LS': { ticker: 'EDP.LS', price: '3.95',   currency: 'EUR', annual_dividend: '0.20', payments_per_year: 1 },
    'VWCE':   { ticker: 'VWCE',   price: '125.40', currency: 'EUR', annual_dividend: '0',    payments_per_year: 0 }
  };
  const quote = quotes[call.request.ticker];
  if (!quote) return cb({ code: grpc.status.NOT_FOUND, message: 'Ticker não encontrado.' });
  cb(null, quote);
}

const server = new grpc.Server();
server.addService(api.MarketDataService.service, { GetQuote });
server.bindAsync('0.0.0.0:50051', grpc.ServerCredentials.createInsecure(), (err, port) => {
  if (err) throw err;
  console.log(`gRPC a escutar na porta ${port}`);
});