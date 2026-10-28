const grpc = require('@grpc/grpc-js');
const loader = require('@grpc/proto-loader');
const path = require('node:path');
const { getQuote } = require('./quotes');

const def = loader.loadSync(path.join(__dirname, '../../proto/market_data.proto'), {
  keepCase: true, longs: String, defaults: true, oneofs: true
});
const api = grpc.loadPackageDefinition(def).marketdata;

async function GetQuote(call, cb) {
  try {
    const quote = await getQuote(call.request.ticker);
    if (!quote) return cb({ code: grpc.status.NOT_FOUND, message: 'Ticker não encontrado.' });
    cb(null, quote);
  } catch (err) {
    cb({ code: grpc.status.UNAVAILABLE, message: 'Não foi possível obter a cotação.' });
  }
}

const server = new grpc.Server();
server.addService(api.MarketDataService.service, { GetQuote });
server.bindAsync('0.0.0.0:50051', grpc.ServerCredentials.createInsecure(), (err, port) => {
  if (err) throw err;
  console.log(`gRPC a escutar na porta ${port}`);
});