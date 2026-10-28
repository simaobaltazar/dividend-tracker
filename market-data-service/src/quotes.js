const YahooFinance = require('yahoo-finance2').default;
const yahooFinance = new YahooFinance();

const paymentsPerYear = { 'AAPL': 4, 'EDP.LS': 1, 'VWCE.DE': 0 };

const cache = new Map();
const CACHE_TTL_MS = 5 * 60 * 1000;

async function getQuote(ticker) {
  const cached = cache.get(ticker);
  if (cached && Date.now() - cached.savedAt < CACHE_TTL_MS) {
    console.log(`[cache] ${ticker}`);
    return cached.quote;
  }

  console.log(`[yahoo] ${ticker}`);
  const result = await yahooFinance.quote(ticker);
  if (!result) return null;

  const quote = {
    ticker: result.symbol,
    price: String(result.regularMarketPrice),
    currency: result.currency,
    annual_dividend: String(result.trailingAnnualDividendRate ?? 0),
    payments_per_year: paymentsPerYear[ticker] ?? 0
  };

  cache.set(ticker, { quote, savedAt: Date.now() });
  return quote;
}

module.exports = { getQuote };