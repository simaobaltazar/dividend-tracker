const YahooFinance = require('yahoo-finance2').default;
const yahooFinance = new YahooFinance();

// O Yahoo não diz quantas vezes por ano cada empresa paga: fica fixo, por agora
const paymentsPerYear = { 'AAPL': 4, 'EDP.LS': 1, 'VWCE.DE': 0 };

async function getQuote(ticker) {
  const quote = await yahooFinance.quote(ticker);
  if (!quote) return null;

  return {
    ticker: quote.symbol,
    price: String(quote.regularMarketPrice),
    currency: quote.currency,
    annual_dividend: String(quote.trailingAnnualDividendRate ?? 0),
    payments_per_year: paymentsPerYear[ticker] ?? 0
  };
}

module.exports = { getQuote };