export default class CurrencyRate {
  private currencyEvolution: Map<string, number>[];
  private baseCurrency: string;
  private fluctuation: number;
  private quotientRate: number;

  public constructor(startCurrency: Object, baseCurrency: string, fluctuation: number = 0.7, quotientRate: number = 0.001) {
    this.currencyEvolution = [new Map(Object.entries(startCurrency))];
    this.baseCurrency = baseCurrency;
    this.fluctuation = fluctuation;
    this.quotientRate = quotientRate;
  }

  public fluctuate(): Map<string, number> {
    let lastCurrency: Map<string, number> = this.getLastCurrencyRate();
    let nextCurrency: Map<string, number> = new Map<string, number>();
    for (let [key, value] of lastCurrency) {
      nextCurrency.set(key, this.nextValue(value, this.computeChangeQuotient(value)));
    }
    nextCurrency.set(this.baseCurrency, 1);
    this.currencyEvolution.push(nextCurrency);
    return nextCurrency;
  }

  public exchange(from: string, to: string, amount: number) {
    const last: Map<string, number> = this.getLastCurrencyRate();
    const toDollars: number = ((last.get(this.baseCurrency) || 0) * amount) / (last.get(from) || 1);
    const result = toDollars * (last.get(to) || 0);
    return result;
  }

  public getLastEvolution(): Map<string, number>[] {
    return this.currencyEvolution;
  }

  public getLastCurrencyRate(): Map<string, number> {
    return this.currencyEvolution[this.currencyEvolution.length - 1];
  }

  public getLastN(n: number): Map<string, number>[] {
    return this.currencyEvolution.slice(-n);
  }

  private nextValue(currentValue: number, quotient: number): number {
    const rise: number = Math.random() - this.fluctuation;
    const result: number = currentValue + rise * quotient;
    return result;
  }

  private computeChangeQuotient(currentValue: number): number {
    return currentValue * this.quotientRate;
  }

}