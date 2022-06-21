import express, { Application, Request, Response } from 'express';
import fs from 'fs';
import cors from 'cors';
import http from 'http';
import { Server } from 'socket.io';
import CurrencyRate from './CurrencyRate.js';
import mapSerializer from './MapSerializer.js';

const config: any = JSON.parse(fs.readFileSync("config.json").toString());
const baseRates: Map<string, number> = JSON.parse(fs.readFileSync(config.ratesFile).toString());
const availableCurrencies = Object.keys(JSON.parse(fs.readFileSync(config.ratesFile).toString()));

const app: Application = express();
app.use(cors(config.corsConfig));
const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: ["http://localhost:8080", "http://localhost:3000"]
  }
});

let currency: CurrencyRate = new CurrencyRate(baseRates, config.baseCurrency, config.fluctuation, config.quotientRate);

setInterval(() => {
    io.emit("latest-currency", mapSerializer(currency.fluctuate()));
  }, config.fluctuatePeriod);

app.get("/rates/latest", (req: Request, res: Response) => {
  res.status(200).send(mapSerializer(currency.getLastCurrencyRate()));
});

app.get("/rates/last/:last", (req: Request, res: Response) => {
  const n: number = +req.params.last;
  res.status(200).send(currency.getLastN(n).map(currencyRate => mapSerializer(currencyRate)));
});

app.get("/rates/evolution", (req: Request, res: Response) => {
  res.status(200).send(currency.getLastEvolution().map(currencyRate => mapSerializer(currencyRate)));
});

app.get("/rates/availableCurrencies", (req: Request, res: Response) => {
  res.status(200).send(availableCurrencies);
});

app.get("/rates/baseCurrency", (req: Request, res: Response) => {
  res.status(200).send(config.baseCurrency);
})

io.on('connection', (socket) => {
  console.log(`a user connected: ${socket.id}`);
});

server.listen(config.port, () => {
  console.log(`Application started on port ${config.port}`);
});
