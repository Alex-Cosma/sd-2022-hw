"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class CurrencyRate {
    constructor(startCurrency, baseCurrency, fluctuation = 0.7, quotientRate = 0.001) {
        this.currencyEvolution = [new Map(Object.entries(startCurrency))];
        this.baseCurrency = baseCurrency;
        this.fluctuation = fluctuation;
        this.quotientRate = quotientRate;
    }
    fluctuate() {
        let lastCurrency = this.getLastCurrencyRate();
        let nextCurrency = new Map();
        for (let [key, value] of lastCurrency) {
            nextCurrency.set(key, this.nextValue(value, this.computeChangeQuotient(value)));
        }
        nextCurrency.set(this.baseCurrency, 1);
        this.currencyEvolution.push(nextCurrency);
        return nextCurrency;
    }
    exchange(from, to, amount) {
        const last = this.getLastCurrencyRate();
        const toDollars = ((last.get(this.baseCurrency) || 0) * amount) / (last.get(from) || 1);
        const result = toDollars * (last.get(to) || 0);
        return result;
    }
    getLastEvolution() {
        return this.currencyEvolution;
    }
    getLastCurrencyRate() {
        return this.currencyEvolution[this.currencyEvolution.length - 1];
    }
    getLastN(n) {
        return this.currencyEvolution.slice(-n);
    }
    nextValue(currentValue, quotient) {
        const rise = Math.random() - this.fluctuation;
        const result = currentValue + rise * quotient;
        return result;
    }
    computeChangeQuotient(currentValue) {
        return currentValue * this.quotientRate;
    }
}
exports.default = CurrencyRate;
